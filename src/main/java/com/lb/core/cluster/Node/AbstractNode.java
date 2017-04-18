package com.lb.core.cluster.Node;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;
import com.lb.core.cluster.MasterElector;
import com.lb.core.cluster.SubscribedNodeManager;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.cmd.HttpCmdServer;
import com.lb.core.cmd.JVMInfoGetHttpCmd;
import com.lb.core.cmd.StatusCheckHttpCmd;
import com.lb.core.command.CommandBodyWrapper;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.commons.utils.GenericsUtils;
import com.lb.core.commons.utils.NetUtils;
import com.lb.core.commons.utils.StringUtils;
import com.lb.core.compiler.AbstractCompiler;
import com.lb.core.constant.ExtConfig;
import com.lb.core.ec.EventCenter;
import com.lb.core.ec.EventInfo;
import com.lb.core.factory.NodeConfigFactory;
import com.lb.core.factory.NodeFactory;
import com.lb.core.factory.RegistryFactory;
import com.lb.core.listener.MasterChangeListener;
import com.lb.core.listener.MasterElectionListener;
import com.lb.core.listener.NodeChangeListener;
import com.lb.core.listener.SelfChangeListener;
import com.lb.core.registry.RegistryStatMonitor;
import com.lb.core.registry.base.AbstractRegistry;
import com.lb.core.registry.base.NotifyEvent;
import com.lb.core.registry.base.NotifyListener;
import com.lb.core.registry.base.Registry;
import com.lb.core.remoting.HeartBeatMonitor;
import com.lb.core.remoting.RemotingClientDelegate;
import com.lb.core.remoting.RemotingServerDelegate;
import com.lb.core.remoting.serialize.AdaptiveSerializable;
import com.lb.core.spi.ServiceLoader;
import com.lb.core.support.AliveKeeping;
import com.lb.core.support.ConfigValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象节点
 * Created by libo on 2017/4/14.
 */
@Slf4j
public abstract class AbstractNode<T extends Node, Context extends AppContext> implements NodeManger {

    protected Registry registry;
    protected T node;
    protected Config config;
    protected Context appContext;
    private List<NodeChangeListener> nodeChangeListeners;
    private List<MasterChangeListener> masterChangeListeners;
    protected AtomicBoolean started = new AtomicBoolean(false);

    private Context getAppContext() {
        try {
            return ((Class<Context>) GenericsUtils.getSuperClassGenericType(
                    this.getClass(), 1)).newInstance();
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
     * 校验config参数
     */
    protected void configValidate() {
        ConfigValidator.validateNodeGroup(config.getNodeGroup());
        ConfigValidator.validateClusterName(config.getClusterName());
        ConfigValidator.validateIdentity(config.getIdentity());
    }

    private void setSpiConfig() {
        // 设置默认序列化方式
        String defaultSerializable = config.getParameter(ExtConfig.REMOTING_SERIALIZABLE_DFT);
        if (StringUtils.isNotEmpty(defaultSerializable)) {
            AdaptiveSerializable.setDefaultSerializable(defaultSerializable);
        }
    }


    /**
     * 初始化配置信息
     */
    protected void initConfig() {

        String compiler = config.getParameter(ExtConfig.COMPILER);
        if (StringUtils.isNotEmpty(compiler)) {
            AbstractCompiler.setCompiler(compiler);
        }

        if (StringUtils.isEmpty(config.getIp())) {
            config.setIp(NetUtils.getLocalHost());
        }
        if (StringUtils.isEmpty(config.getIdentity())) {
            NodeConfigFactory.buildIdentity(config);
        }
        NodeFactory.build(node, config);

        log.info("Current Node config :{}", config);

        appContext.setEventCenter(ServiceLoader.load(EventCenter.class, config));

        appContext.setCommandBodyWrapper(new CommandBodyWrapper(config));
        appContext.setMasterElector(new MasterElector(appContext));
        appContext.getMasterElector().addMasterChangeListener(masterChangeListeners);
        appContext.setRegistryStatMonitor(new RegistryStatMonitor(appContext));

        // 订阅的node管理
        SubscribedNodeManager subscribedNodeManager = new SubscribedNodeManager(appContext);
        appContext.setSubscribedNodeManager(subscribedNodeManager);
        nodeChangeListeners.add(subscribedNodeManager);
        // 用于master选举的监听器
        nodeChangeListeners.add(new MasterElectionListener(appContext));
        // 监听自己节点变化（如，当前节点被禁用了）
        nodeChangeListeners.add(new SelfChangeListener(appContext));

        setSpiConfig();
    }

    // 初始化命令中心
    private void initHttpCmdServer() {
        // 命令中心
        int port = appContext.getConfig().getParameter(ExtConfig.HTTP_CMD_PORT, 8719);
        appContext.setHttpCmdServer(HttpCmdServer.Factory.getHttpCmdServer(config.getIp(), port));

        // 先启动，中间看端口是否被占用
        appContext.getHttpCmdServer().start();
        // 设置command端口，会暴露到注册中心上
        node.setHttpCmdPort(appContext.getHttpCmdServer().getPort());

        appContext.getHttpCmdServer().registerCommands(
                new StatusCheckHttpCmd(appContext.getConfig()),
                new JVMInfoGetHttpCmd(appContext.getConfig()));        // 状态检查
    }

    // 初始化注册中心
    private void initRegistry() {
        registry = RegistryFactory.getRegistry(appContext);
        if (registry instanceof AbstractRegistry) {
            ((AbstractRegistry) registry).setNode(node);
        }
        registry.subscribe(node, new NotifyListener() {
            private final Logger NOTIFY_log = LoggerFactory.getLogger(NotifyListener.class);

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
                                NOTIFY_log.error("{} add nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                    case REMOVE:
                        for (NodeChangeListener listener : nodeChangeListeners) {
                            try {
                                listener.removeNodes(nodes);
                            } catch (Throwable t) {
                                NOTIFY_log.error("{} remove nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                }
            }
        });
    }


    public AbstractNode() {
        this.appContext = this.getAppContext();
        this.node = NodeFactory.create(getNodeClass());
        this.config = NodeConfigFactory.getDefaultConfig();
        this.config.setNodeType(node.getNodeType());
        this.appContext.setConfig(config);
        this.nodeChangeListeners = new ArrayList<NodeChangeListener>();
        this.masterChangeListeners = new ArrayList<MasterChangeListener>();
    }

    protected abstract void remotingStart();

    protected abstract void remotingStop();

    protected abstract void beforeRemotingStart();

    protected abstract void afterRemotingStart();

    protected abstract void beforeRemotingStop();

    protected abstract void afterRemotingStop();

    /**
     * 启动节点
     */
    @Override
    final public void start() {
        try {
            if (started.compareAndSet(false, true)) {

                configValidate();

                // 初始化配置
                initConfig();

                // 初始化HttpCmdServer
                initHttpCmdServer();

                beforeRemotingStart();

                remotingStart();

                afterRemotingStart();

                initRegistry();

                registry.register(node);

                AliveKeeping.start();

                log.info("========== Start success, nodeType={}, identity={}", config.getNodeType(), config.getIdentity());
            }
        } catch (Throwable e) {
            if (e.getMessage().contains("Address already in use")) {
                log.error("========== Start failed at listen port {}, nodeType={}, identity={}",
                        config.getListenPort(), config.getNodeType(), config.getIdentity(), e);
            } else {
                log.error("========== Start failed, nodeType={}, identity={}",
                        config.getNodeType(), config.getIdentity(), e);
            }
        }
    }

    /**
     * 停掉节点
     */
    @Override
    final public void stop() {
        try {
            if (started.compareAndSet(true, false)) {

                if (registry != null) {
                    registry.unRegister(node);
                }

                beforeRemotingStop();

                remotingStop();

                afterRemotingStop();

                appContext.getEventCenter().publishSync(new EventInfo(EcTopic.NODE_SHUT_DOWN));

                AliveKeeping.stop();

                log.info("========== Stop success, nodeType={}, identity={}", config.getNodeType(), config.getIdentity());
            }
        } catch (Throwable e) {
            log.error("========== Stop failed, nodeType={}, identity={}", config.getNodeType(), config.getIdentity(), e);
        }
    }

    /**
     * 销毁节点
     */
    @Override
    public void destroy() {
        try {
            registry.destroy();
            log.info("Destroy success, nodeType={}, identity={}", config.getNodeType(), config.getIdentity());
        } catch (Throwable e) {
            log.error("Destroy failed, nodeType={}, identity={}", config.getNodeType(), config.getIdentity(), e);
        }
    }

    /**
     * 设置zookeeper注册中心地址
     */
    public void setRegistryAddress(String registryAddress) {
        config.setRegistryAddress(registryAddress);
    }

    /**
     * 设置远程调用超时时间
     */
    public void setInvokeTimeoutMillis(int invokeTimeOutMillis) {
        config.setInvokeTimeOutMillis(invokeTimeOutMillis);
    }

    /**
     * 设置集群名字
     */
    public void setClusterName(String clusterName) {
        config.setClusterName(clusterName);
    }

    /**
     * 节点标识(必须要保证这个标识是唯一的才能设置，请谨慎设置)
     * 这个是非必须设置的，建议使用系统默认生成
     */
    public void setIdentity(String identity) {
        config.setIdentity(identity);
    }

    /**
     * 添加节点监听器
     */
    public void addNodeChangeListener(NodeChangeListener notifyListener) {
        if (notifyListener != null) {
            nodeChangeListeners.add(notifyListener);
        }
    }

    /**
     * 显示设置绑定ip
     */
    public void setBindIp(String bindIp) {
        if (StringUtils.isEmpty(bindIp)
                || !NetUtils.isValidHost(bindIp)
                ) {
            throw new IllegalArgumentException("Invalided bind ip:" + bindIp);
        }
        config.setIp(bindIp);
    }

    /**
     * 添加 master 节点变化监听器
     */
    public void addMasterChangeListener(MasterChangeListener masterChangeListener) {
        if (masterChangeListener != null) {
            masterChangeListeners.add(masterChangeListener);
        }
    }

    public void setDataPath(String path) {
        if (StringUtils.isNotEmpty(path)) {
            config.setDataPath(path);
        }
    }

    /**
     * 设置额外的配置参数
     */
    public void addConfig(String key, String value) {
        config.setParameter(key, value);
    }
}
