package com.lb.core.remoting;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.command.HeartBeatRequest;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.ec.EventInfo;
import com.lb.core.ec.EventSubscriber;
import com.lb.core.ec.Observer;
import com.lb.core.factory.NamedThreadFactory;
import com.lb.core.support.JobProtos;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by libo on 2017/4/18.
 */
@Slf4j
public class HeartBeatMonitor {

    // 用来定时发送心跳
    private final ScheduledExecutorService PING_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1, new NamedThreadFactory("LTD-HeartBeat-Ping", true));
    private ScheduledFuture<?> pingScheduledFuture;

    // 当没有可用的JobDispatch时，启动这个来快速检查
    private final ScheduledExecutorService FAST_PING_EXECUTOR = Executors.newScheduledThreadPool(1, new NamedThreadFactory("LTD-HeartBeat-Fast-Ping", true));
    private ScheduledFuture<?> fastPingScheduledFuture;

    private RemotingClientDelegate remotingClient;
    private AppContext appContext;
    private EventSubscriber jobDispatchUnavailableEventSubscriber;

    public HeartBeatMonitor(RemotingClientDelegate remotingClient, AppContext appContext) {
        this.remotingClient = remotingClient;
        this.appContext = appContext;
        this.jobDispatchUnavailableEventSubscriber = new EventSubscriber(HeartBeatMonitor.class.getName()
                + "_PING_" + appContext.getConfig().getIdentity(),
                new Observer() {
                    @Override
                    public void onObserver(EventInfo eventInfo) {
                        startFastPing();
                        stopPing();
                    }
                });
        appContext.getEventCenter().subscribe(new EventSubscriber(HeartBeatMonitor.class.getName()
                + "_NODE_ADD_" + appContext.getConfig().getIdentity(), new Observer() {
            @Override
            public void onObserver(EventInfo eventInfo) {
                Node node = (Node) eventInfo.getParam("node");
                if (node == null || NodeType.DISPATCH_NODE != node.getNodeType()) {
                    return;
                }
                try {
                    check(node);
                } catch (Throwable ignore) {
                }
            }
        }), EcTopic.NODE_ADD);
    }

    private AtomicBoolean pingStart = new AtomicBoolean(false);
    private AtomicBoolean fastPingStart = new AtomicBoolean(false);

    public void start() {
        startFastPing();
    }

    public void stop() {
        stopPing();
        stopFastPing();
    }

    private void startPing() {
        try {
            if (pingStart.compareAndSet(false, true)) {
                // 用来监听 JobTracker不可用的消息，然后马上启动 快速检查定时器
                appContext.getEventCenter().subscribe(jobDispatchUnavailableEventSubscriber, EcTopic.NO_JOB_DISPATCH_AVAILABLE);
                if (pingScheduledFuture == null) {
                    pingScheduledFuture = PING_EXECUTOR_SERVICE.scheduleWithFixedDelay(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if (pingStart.get()) {
                                        ping();
                                    }
                                }
                            }, 30, 30, TimeUnit.SECONDS);      // 30s 一次心跳
                }
                log.debug("Start slow ping success.");
            }
        } catch (Throwable t) {
            log.error("Start slow ping failed.", t);
        }
    }

    private void stopPing() {
        try {
            if (pingStart.compareAndSet(true, false)) {
                appContext.getEventCenter().unSubscribe(EcTopic.NO_JOB_DISPATCH_AVAILABLE, jobDispatchUnavailableEventSubscriber);
                log.debug("Stop slow ping success.");
            }
        } catch (Throwable t) {
            log.error("Stop slow ping failed.", t);
        }
    }

    private void startFastPing() {
        if (fastPingStart.compareAndSet(false, true)) {
            try {
                // 2s 一次进行检查
                if (fastPingScheduledFuture == null) {
                    fastPingScheduledFuture = FAST_PING_EXECUTOR.scheduleWithFixedDelay(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if (fastPingStart.get()) {
                                        ping();
                                    }
                                }
                            }, 500, 500, TimeUnit.MILLISECONDS);
                }
                log.debug("Start fast ping success.");
            } catch (Throwable t) {
                log.error("Start fast ping failed.", t);
            }
        }
    }

    private void stopFastPing() {
        try {
            if (fastPingStart.compareAndSet(true, false)) {
                log.debug("Stop fast ping success.");
            }
        } catch (Throwable t) {
            log.error("Stop fast ping failed.", t);
        }
    }

    private AtomicBoolean running = new AtomicBoolean(false);

    private void ping() {
        try {
            if (running.compareAndSet(false, true)) {
                // to ensure only one thread go there
                try {
                    check();
                } finally {
                    running.compareAndSet(true, false);
                }
            }
        } catch (Throwable t) {
            log.error("Ping JobTracker error", t);
        }
    }


    private void check() {
        List<Node> dispatchs = appContext.getSubscribedNodeManager().getNodeList(NodeType.DISPATCH_NODE);
        if (CollectionUtils.isEmpty(dispatchs)) {
            return;
        }
        for (Node dispatch : dispatchs) {
            check(dispatch);
        }
    }

    private void check(Node dispatch) {
        // 每个JobTracker 都要发送心跳
        if (beat(remotingClient, dispatch.getAddress())) {
            remotingClient.addJobTracker(dispatch);
            if (!remotingClient.isServerEnable()) {
                remotingClient.setServerEnable(true);
                appContext.getEventCenter().publishAsync(new EventInfo(EcTopic.JOB_DISPATCH_AVAILABLE));
            } else {
                remotingClient.setServerEnable(true);
            }
            stopFastPing();
            startPing();
        } else {
            remotingClient.removeJobTracker(dispatch);
        }
    }

    /**
     * 发送心跳
     */
    private boolean beat(RemotingClientDelegate remotingClient, String addr) {

        HeartBeatRequest commandBody = appContext.getCommandBodyWrapper().wrapper(new HeartBeatRequest());

        RemotingCommand request = RemotingCommand.createRequestCommand(
                JobProtos.RequestCode.HEART_BEAT.code(), commandBody);
        try {
            RemotingCommand response = remotingClient.getRemotingClient().invokeSync(addr, request, 5000);
            if (response != null && JobProtos.ResponseCode.HEART_BEAT_SUCCESS ==
                    JobProtos.ResponseCode.valueOf(response.getCode())) {
                if (log.isDebugEnabled()) {
                    log.debug("heart beat success. ");
                }
                return true;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return false;
    }


}
