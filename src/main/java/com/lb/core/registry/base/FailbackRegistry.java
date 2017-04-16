package com.lb.core.registry.base;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.commons.concurrent.ConcurrentHashSet;
import com.lb.core.constant.Constants;
import com.lb.core.constant.ExtConfig;
import com.lb.core.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * 抽象注册中心
 * <p>
 *     可自动恢复
 * </p>
 * Created by libo on 2017/4/15.
 */
@Slf4j
public abstract class FailbackRegistry extends AbstractRegistry{

    // 定时任务执行器
    private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("LTD RegistryFailedRetryTimer", true));

    // 失败重试定时器，定时检查是否有请求失败，如有，无限次重试
    private ScheduledFuture<?> retryFuture;

    // 注册失败的定时重试
    private final Set<Node> failedRegistered = new ConcurrentHashSet<Node>();
    private final Set<Node> failedUnRegistered = new ConcurrentHashSet<Node>();
    private final ConcurrentMap<Node, Set<NotifyListener>> failedSubscribed = new ConcurrentHashMap<Node, Set<NotifyListener>>();
    private final ConcurrentMap<Node, Set<NotifyListener>> failedUnsubscribed = new ConcurrentHashMap<Node, Set<NotifyListener>>();
    private final ConcurrentMap<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>> failedNotified = new ConcurrentHashMap<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>>();

    public FailbackRegistry(AppContext appContext) {
        super(appContext);
        
        int retryPeriod = appContext.getConfig().getParameter(ExtConfig.REGISTRY_RETRY_PERIOD_KEY, 
                Constants.DEFAULT_REGISTRY_RETRY_PERIOD);
        this.retryFuture = retryExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    retry();
                }catch (Throwable throwable){
                    log.error("Unexpected error occur at failed retry, cause: " + throwable.getMessage(), throwable);
                }
            }
        }, retryPeriod, retryPeriod, TimeUnit.MILLISECONDS);
    }

    protected void retry() {
        if (!failedRegistered.isEmpty()) {
            Set<Node> failed = new HashSet<Node>(failedRegistered);
            if (failed.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Retry register {}", failed);
                }
                try {
                    for (Node node : failed) {
                        doRegister(node);
                        failedRegistered.remove(node);
                    }
                } catch (Throwable t) {     // 忽略所有异常，等待下次重试
                    log.warn("Failed to retry register " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                }
            }
        }
        if (!failedUnRegistered.isEmpty()) {
            Set<Node> failed = new HashSet<Node>(failedUnRegistered);
            if (failed.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Retry unregister {}", failed);
                }
                try {
                    for (Node node : failed) {
                        doUnRegister(node);
                        failedUnRegistered.remove(node);
                    }
                } catch (Throwable t) {     // 忽略所有异常，等待下次重试
                    log.warn("Failed to retry unregister " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                }
            }
        }
        if (!failedSubscribed.isEmpty()) {
            Map<Node, Set<NotifyListener>> failed = new HashMap<Node, Set<NotifyListener>>(failedSubscribed);
            for (Map.Entry<Node, Set<NotifyListener>> entry : new HashMap<Node, Set<NotifyListener>>(failed).entrySet()) {
                if (entry.getValue() == null || entry.getValue().size() == 0) {
                    failed.remove(entry.getKey());
                }
            }
            if (failed.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Retry subscribe " + failed);
                }
                try {
                    for (Map.Entry<Node, Set<NotifyListener>> entry : failed.entrySet()) {
                        Node node = entry.getKey();
                        Set<NotifyListener> listeners = entry.getValue();
                        for (NotifyListener listener : listeners) {
                            try {
                                doSubscribe(node, listener);
                                listeners.remove(listener);
                                failedSubscribed.remove(entry.getKey());
                            } catch (Throwable t) { // 忽略所有异常，等待下次重试
                                log.warn("Failed to retry subscribe " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                            }
                        }
                    }
                } catch (Throwable t) { // 忽略所有异常，等待下次重试
                    log.warn("Failed to retry subscribe " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                }
            }
        }
        if (!failedUnsubscribed.isEmpty()) {
            Map<Node, Set<NotifyListener>> failed = new HashMap<Node, Set<NotifyListener>>(failedUnsubscribed);
            for (Map.Entry<Node, Set<NotifyListener>> entry : new HashMap<Node, Set<NotifyListener>>(failed).entrySet()) {
                if (entry.getValue() == null || entry.getValue().size() == 0) {
                    failed.remove(entry.getKey());
                }
            }
            if (failed.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Retry unSubscribe " + failed);
                }
                try {
                    for (Map.Entry<Node, Set<NotifyListener>> entry : failed.entrySet()) {
                        Node node = entry.getKey();
                        Set<NotifyListener> listeners = entry.getValue();
                        for (NotifyListener listener : listeners) {
                            try {
                                doUnSubscribe(node, listener);
                                listeners.remove(listener);
                            } catch (Throwable t) { // 忽略所有异常，等待下次重试
                                log.warn("Failed to retry unSubscribe " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                            }
                        }
                    }
                } catch (Throwable t) { // 忽略所有异常，等待下次重试
                    log.warn("Failed to retry unSubscribe " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                }
            }
        }
        if (!failedNotified.isEmpty()) {
            Map<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>> failed = new HashMap<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>>(failedNotified);
            for (Map.Entry<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>> entry : new HashMap<Node, Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>>>(failed).entrySet()) {
                if (entry.getValue() == null || entry.getValue().size() == 0) {
                    failed.remove(entry.getKey());
                }
            }
            if (failed.size() > 0) {
                if (log.isInfoEnabled()) {
                    log.info("Retry notify " + failed);
                }
                try {
                    for (Map<NotifyListener, NotifyPair<NotifyEvent, List<Node>>> values : failed.values()) {
                        for (Map.Entry<NotifyListener, NotifyPair<NotifyEvent, List<Node>>> entry : values.entrySet()) {
                            try {
                                NotifyListener listener = entry.getKey();
                                NotifyPair<NotifyEvent, List<Node>> notifyPair = entry.getValue();
                                listener.notify(notifyPair.event, notifyPair.nodes);
                                values.remove(listener);
                            } catch (Throwable t) { // 忽略所有异常，等待下次重试
                                log.warn("Failed to retry notify " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                            }
                        }
                    }
                } catch (Throwable t) { // 忽略所有异常，等待下次重试
                    log.warn("Failed to retry notify " + failed + ", waiting for again, cause: " + t.getMessage(), t);
                }
            }
        }
    }


    private class NotifyPair<T1, T2> {
        T1 event;
        T2 nodes;

        public NotifyPair(T1 event, T2 nodes) {
            this.event = event;
            this.nodes = nodes;
        }
    }

    protected abstract void doRegister(Node node);

    protected abstract void doUnRegister(Node node);

    protected abstract void doSubscribe(Node node, NotifyListener listener);

    protected abstract void doUnSubscribe(Node node, NotifyListener listener);

}
