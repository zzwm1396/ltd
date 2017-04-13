package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.cmd.HttpCmdServer;
import com.lb.core.cmd.JVMInfoGetHttpCmd;
import com.lb.core.cmd.StatusCheckHttpCmd;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.commons.utils.GenericsUtils;
import com.lb.core.commons.utils.NetUtils;
import com.lb.core.constant.EcTopic;
import com.lb.core.constant.ExtConfig;
import com.lb.core.ec.EventCenter;
import com.lb.core.ec.EventInfo;
import com.lb.core.enums.NotifyEvent;
import com.lb.core.factory.JobNodeConfigFactory;
import com.lb.core.factory.NodeFactory;
import com.lb.core.listener.MasterChangeListener;
import com.lb.core.listener.MasterElectionListener;
import com.lb.core.listener.NodeChangeListener;
import com.lb.core.listener.SelfChangeListener;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;
import com.lb.core.registry.*;
import com.lb.core.spi.ServiceLoader;
import com.lb.core.support.AliveKeeping;
import com.lb.core.support.ConfigValidator;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Created by libo on 2017/4/7.
 */
public abstract class AbstractJobNode<T extends Node, Context extends AppContext> implements JobNode{

    private static Logger LOGGER = LoggerFactory.getLogger(JobNode.class);

    protected Registry registry;

    protected T node;

    protected Config config;

    protected Context appContext;

    private List<NodeChangeListener> nodeChangeListeners;

    private List<MasterChangeListener> masterChangeListeners;

    protected AtomicBoolean started = new AtomicBoolean(false);

    public AbstractJobNode(){
        appContext = getAppContext();
        node = NodeFactory.create(getNodeClass());
        config = JobNodeConfigFactory.getDefaultConfig();
        config.setNodeType(node.getNodeType());

    }

    @SuppressWarnings("unchecked")
    private Context getAppContext() {
        try {
            return ((Class<Context>)
                    GenericsUtils.getSuperClassGenericType(this.getClass(), 1))
                    .newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getNodeClass() {
        return (Class<T>)
                GenericsUtils.getSuperClassGenericType(this.getClass(), 0);
    }

    /**
     * 设置zookeeper注册中心地址
     * @param registryAddress 注册中心地址
     */
    public void setRegistryAddress(String registryAddress){
        config.setRegistryAddress(registryAddress);
    }

    /**
     * 设置远程调用超时时间
     * @param invokeTimeOutMillis  超时时间
     */
    public void setInvokeTimeOutMillis(int invokeTimeOutMillis){
        config.setInvokeTimeOutMillis(invokeTimeOutMillis);
    }

    /**
     * 设置集群名字
     * @param clusterName 集群名字
     */
    public void setClusterName(String clusterName){
        config.setClusterName(clusterName);
    }

    /**
     * 节点标识（必须保证这个标识是唯一的才能设置，清谨慎设置）
     * 这个是非必须设置，建议使用系统自动生成
     * @param identity
     */
    public void setIdentity(String identity){
        config.setIdentity(identity);
    }

    /**
     * 添加节点监听器
     * @param nodeChangeListener
     */
    public void addNodeChangeListener(NodeChangeListener nodeChangeListener){
        if (nodeChangeListener != null){
            nodeChangeListeners.add(nodeChangeListener);
        }
    }

    /**
     * 显式设置绑定ip
     * @param bindIp
     */
    public void setBindIp(String bindIp){
        if((bindIp != null && !"".equals(bindIp)) || !NetUtils.isValidHost(bindIp)){
            throw new IllegalArgumentException("Invalided bind ip: " + bindIp);
        }
        config.setIp(bindIp);
    }

    /**
     * 添加 master 节点变化监视器
     * @param masterChangeListener
     */
    public void addMasterChangeListener(MasterChangeListener masterChangeListener){
        if (masterChangeListener != null){
            masterChangeListeners.add(masterChangeListener);
        }
    }

    public void setDataPath(String path){
        if (path == null || "".equals(path)){
            config.setDataPath(path);
        }
    }


    public void addConfig(String key, String value){
        config.setParameter(key, value);
    }

    protected void configValidate(){
        ConfigValidator.validateNodeGroup(config.getNodeGroup());
        ConfigValidator.validateClusterName(config.getClusterName());
        ConfigValidator.validateIdentity(config.getIdentity());
    }

    protected void initConfig(){
        String compiler = config.getParameter(ExtConfig.COMPILER);

        /**
         * 有问题
         * 需要继续追踪
         */
        if (compiler != null && !"".equals(compiler.trim())){

        }

        if (config.getIp() == null || "".equals(config.getIp().trim())){
            config.setIp(NetUtils.getLocalHost());
        }

        if (config.getIdentity() == null || "".equals(config.getIdentity().trim())){
            JobNodeConfigFactory.buildIdentity(config);
        }

        NodeFactory.build(node, config);

        LOGGER.info("Current Node config:{}", config);

        appContext.setEventCenter(ServiceLoader.load(EventCenter.class, config));

        appContext.setMasterElector(new MasterElector(appContext));
        appContext.getMasterElector().addMasterChangeLinstener(masterChangeListeners);
        appContext.setRegistryStatMonitor(new RegistryStatMonitor(appContext));

        // 订阅的node管理
        SubscribedNodeManager subscribedNodeManager = new SubscribedNodeManager(appContext);
        appContext.setSubscribedNodeManager(subscribedNodeManager);
        nodeChangeListeners.add(subscribedNodeManager);
        // master 选举的监听器
        nodeChangeListeners.add(new MasterElectionListener(appContext));

        nodeChangeListeners.add(new SelfChangeListener(appContext));

    }


    private void initHttpCmdServer(){
        int port = appContext.getConfig().getParameter(ExtConfig.HTTP_CMD_PORT, 8719);
        appContext.setHttpCmdServer(HttpCmdServer.Factory.getHttpCmdServer(config.getIp(), port));

        appContext.getHttpCmdServer().start();
        node.setHttpCmdPort(appContext.getHttpCmdServer().getPort());

        appContext.getHttpCmdServer().registerCommands(
                new StatusCheckHttpCmd(appContext.getConfig()),
                new JVMInfoGetHttpCmd(appContext.getConfig())
        );  // 状态检查

    }

    protected abstract void remotingStart();

    protected abstract void remotingStop();

    protected abstract void beforeRemotingStart();

    protected abstract void afterRemotingStart();

    protected abstract void beforeRemotingStop();

    protected abstract void afterRemotingStop();

    private void initRegistry(){
        registry = RegistryFactory.getRegistry(appContext);
        if (registry instanceof AbstractRegistry) {
            ((AbstractRegistry) registry).setNode(node);
        }
        registry.subscribe(node, new NotifyListener() {
            private final Logger NOTIFY_LOGGER = LoggerFactory.getLogger(NotifyListener.class);

            @Override
            public void notify(NotifyEvent event, List<Node> nodes) {
                if (CollectionUtils.isEmpty(nodes)) {
                    return;
                }
                switch (event) {
                    case ADD:
                        for (NodeChangeListener listener : nodeChangeListeners) {
                            try {
                                listener.addNodes(nodes);
                            } catch (Throwable t) {
                                NOTIFY_LOGGER.error("{} add nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                    case REMOVE:
                        for (NodeChangeListener listener : nodeChangeListeners) {
                            try {
                                listener.removeNodes(nodes);
                            } catch (Throwable t) {
                                NOTIFY_LOGGER.error("{} remove nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                }
            }
        });
    }




    @Override
    public void start() {
        try{
            if (started.compareAndSet(false, true)){
                configValidate();

                initConfig();

                // 初始化HttpCmdServer
                initHttpCmdServer();

                beforeRemotingStart();

                remotingStart();

                afterRemotingStart();

                initRegistry();

                registry.register(node);

                AliveKeeping.start();

                LOGGER.info("========== Start success, nodeType={}, identity={}",
                        config.getNodeType(), config.getIdentity());
            }
        } catch (Throwable e){
            if (e.getMessage().contains("Address already in use")) {
                LOGGER.error("========== Start failed at listen port {}, nodeType={}, identity={}",
                        config.getListenPort(), config.getNodeType(), config.getIdentity(), e);
            } else {
                LOGGER.error("========== Start failed, nodeType={}, identity={}",
                        config.getNodeType(), config.getIdentity(), e);
            }
        }
    }

    @Override
    public void stop() {
        try {
            if (started.compareAndSet(true, false)) {

                if (registry != null) {
                    registry.unregister(node);
                }

                beforeRemotingStop();

                remotingStop();

                afterRemotingStop();

                appContext.getEventCenter().publishSync(new EventInfo(EcTopic.NODE_SHUT_DOWN));

                AliveKeeping.stop();

                LOGGER.info("========== Stop success, nodeType={}, identity={}", config.getNodeType(), config.getIdentity());
            }
        } catch (Throwable e) {
            LOGGER.error("========== Stop failed, nodeType={}, identity={}", config.getNodeType(), config.getIdentity(), e);
        }
    }

    @Override
    public void destroy() {
        try {
            registry.destroy();
            LOGGER.info("Destroy success, nodeType={}, identity={}", config.getNodeType(), config.getIdentity());
        } catch (Throwable e) {
            LOGGER.error("Destroy failed, nodeType={}, identity={}", config.getNodeType(), config.getIdentity(), e);
        }
    }
}
