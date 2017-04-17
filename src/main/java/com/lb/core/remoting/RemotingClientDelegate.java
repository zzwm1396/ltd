package com.lb.core.remoting;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.constant.ExtConfig;
import com.lb.core.ec.EventInfo;
import com.lb.core.exception.JobDispatchNotFoundException;
import com.lb.core.loadbalance.LoadBalance;
import com.lb.core.remoting.exception.RemotingException;
import com.lb.core.spi.ServiceLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * remotingClient 包装类
 * Created by libo on 2017/4/17.
 */
@Slf4j
@Getter
@Setter
public class RemotingClientDelegate {
    private RemotingClient remotingClient;
    private AppContext appContext;

    // JobDispatch 是否可用
    private volatile boolean serverEnable = false;
    private List<Node> jobDispatchs;

    public RemotingClientDelegate(RemotingClient remotingClient, AppContext appContext) {
        this.remotingClient = remotingClient;
        this.appContext = appContext;
        this.jobDispatchs = new CopyOnWriteArrayList<Node>();
    }

    private Node getJobDispatchsNode() throws JobDispatchNotFoundException {
        try {
            if (jobDispatchs.size() == 0) {
                throw new JobDispatchNotFoundException("no available jobDispatch!");
            }
            // 连JobTracker的负载均衡算法
            LoadBalance loadBalance = ServiceLoader.load(LoadBalance.class, appContext.getConfig(), ExtConfig.JOB_DISPATCH_SELECT_LOADBALANCE);
            return loadBalance.select(jobDispatchs, appContext.getConfig().getIdentity());
        } catch (JobDispatchNotFoundException e) {
            this.serverEnable = false;
            // publish msg
            EventInfo eventInfo = new EventInfo(EcTopic.NO_JOB_DISPATCH_AVAILABLE);
            appContext.getEventCenter().publishAsync(eventInfo);
            throw e;
        }
    }

    public void start() {
        try {
            remotingClient.start();
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(Node jobDispatch) {
        return jobDispatchs.contains(jobDispatch);
    }

    public void addJobTracker(Node jobDispatch) {
        if (!contains(jobDispatch)) {
            jobDispatchs.add(jobDispatch);
        }
    }

    public boolean removeJobTracker(Node jobDispatch) {
        return jobDispatchs.remove(jobDispatch);
    }

    /**
     * 同步调用
     */
    public RemotingCommand invokeSync(RemotingCommand request)
            throws JobDispatchNotFoundException {

        Node jobDispatch = getJobDispatchsNode();

        try {
            RemotingCommand response = remotingClient.invokeSync(jobDispatch.getAddress(),
                    request, appContext.getConfig().getInvokeTimeOutMillis());
            this.serverEnable = true;
            return response;
        } catch (Exception e) {
            // 将这个JobTracker移除
            jobDispatchs.remove(jobDispatch);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e1) {
                log.error(e1.getMessage(), e1);
            }
            // 只要不是节点 不可用, 轮询所有节点请求
            return invokeSync(request);
        }
    }

    /**
     * 异步调用
     */
    public void invokeAsync(RemotingCommand request, AsyncCallback asyncCallback)
            throws JobDispatchNotFoundException {

        Node jobDispatch = getJobDispatchsNode();

        try {
            remotingClient.invokeAsync(jobDispatch.getAddress(), request,
                    appContext.getConfig().getInvokeTimeOutMillis(), asyncCallback);
            this.serverEnable = true;
        } catch (Throwable e) {
            // 将这个JobTracker移除
            jobDispatchs.remove(jobDispatch);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e1) {
                log.error(e1.getMessage(), e1);
            }
            // 只要不是节点 不可用, 轮询所有节点请求
            invokeAsync(request, asyncCallback);
        }
    }

    /**
     * 单向调用
     */
    public void invokeOneway(RemotingCommand request)
            throws JobDispatchNotFoundException {

        Node jobDispatch = getJobDispatchsNode();

        try {
            remotingClient.invokeOneway(jobDispatch.getAddress(), request,
                    appContext.getConfig().getInvokeTimeOutMillis());
            this.serverEnable = true;
        } catch (Throwable e) {
            // 将这个JobTracker移除
            jobDispatchs.remove(jobDispatch);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e1) {
                log.error(e1.getMessage(), e1);
            }
            // 只要不是节点 不可用, 轮询所有节点请求
            invokeOneway(request);
        }
    }

    public void registerProcessor(int requestCode, RemotingProcessor processor,
                                  ExecutorService executor) {
        remotingClient.registerProcessor(requestCode, processor, executor);
    }

    public void registerDefaultProcessor(RemotingProcessor processor, ExecutorService executor) {
        remotingClient.registerDefaultProcessor(processor, executor);
    }



    public void shutdown() {
        remotingClient.shutdown();
    }

}
