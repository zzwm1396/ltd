package com.lb.core.registry.base;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.commons.concurrent.ConcurrentHashSet;
import com.lb.core.commons.utils.CollectionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象注册中心
 * Created by libo on 2017/4/15.
 */
@Slf4j
@Setter
@Getter
public class AbstractRegistry implements Registry {

    /**
     * 注册节点信息集合
     */
    private final Set<Node> registered = new ConcurrentHashSet<>();

    /**
     * 监听节点信息集合
     */
    private final ConcurrentHashMap<Node, Set<NotifyListener>> subscribed = new ConcurrentHashMap<>();


    private AppContext appContext;
    private Node node;

    public AbstractRegistry(AppContext appContext){
        this.appContext = appContext;
    }


    @Override
    public void register(Node node) {
        if (node == null)
            throw new IllegalArgumentException("register node == null");
        if (log.isInfoEnabled()){
            log.info("Register: " + node);
        }
        registered.add(node);
    }

    @Override
    public void unRegister(Node node) {
        if (node == null)
            throw new IllegalArgumentException("unRegister node == null");
        if (log.isInfoEnabled()){
            log.info("unRegister: " + node);
        }
        registered.remove(node);
    }

    @Override
    public void subscribe(Node node, NotifyListener listener) {
        if (node == null)
            throw new IllegalArgumentException("subscribe node == null");
        if (listener == null)
            throw new IllegalArgumentException("subscribe listener == null");
        if (log.isInfoEnabled()){
            log.info("Subscribe: " + node);
        }
        Set<NotifyListener> listeners = subscribed.get(node);
        if (listeners == null){
            subscribed.putIfAbsent(node, new ConcurrentHashSet<NotifyListener>());
            listeners = subscribed.get(node);
        }
        listeners.add(listener);
    }

    @Override
    public void unSubscribe(Node node, NotifyListener listener) {
        if (node == null)
            throw new IllegalArgumentException("unSubscribe node == null");
        if (listener == null)
            throw new IllegalArgumentException("unSubscribe listener == null");
        if (log.isInfoEnabled()){
            log.info("unSubscribe: " + node);
        }
        Set<NotifyListener> listeners = subscribed.get(node);
        if (listeners != null){
            listeners.remove(listener);
        }
    }

    @Override
    public void destroy() {
        if (log.isInfoEnabled())
            log.info("Destroy registry:" + this.getNode());

        Set<Node> destroyRegistered = new HashSet<>(getRegistered());
        if (!destroyRegistered.isEmpty()){
            for (Node node : destroyRegistered) {
                try {
                    unRegister(node);
                    if (log.isInfoEnabled()){
                        log.info("Destroy --->:  unRegister node: " + node);
                    }
                } catch (Throwable throwable) {
                    log.warn("Destroy --->: Failed to unRegister node" + node + " to registry "
                            + this.getNode() + " on destroy, cause: " + throwable.getMessage(), throwable);
                }
            }
        }

        Map<Node, Set<NotifyListener>> destroySubscribe = new HashMap<>(this.getSubscribed());
        if (!destroySubscribe.isEmpty()){
            for (Map.Entry<Node, Set<NotifyListener>> entry : destroySubscribe.entrySet()) {
                Node node = entry.getKey();
                for (NotifyListener listener : entry.getValue()) {
                    try {
                        unSubscribe(node, listener);
                        if (log.isInfoEnabled()){
                            log.info("Destroy --->:   unSubscribe node " + node);
                        }
                    } catch (Throwable throwable) {
                        log.warn("Failed to unubscribe node " + node + " to registry "
                                + getNode() + " on destroy, cause: " + throwable.getMessage(), throwable);
                    }
                }
            }
        }

    }

    /**
     * 通知节点变化
     * @param event 通知事件
     * @param nodes 被通知的节点
     * @param listener 监听的方式
     */
    protected void notify(NotifyEvent event, List<Node> nodes, NotifyListener listener){
        if (event == null)
            throw new IllegalArgumentException("notify event == null");
        if (listener == null)
            throw new IllegalArgumentException("notify listener == null");
        if (CollectionUtils.isEmpty(nodes)){
            log.warn("Ignore empty notify nodes for subscribe node " + this.getNode());
            return;
        }

        listener.notify(event, nodes);
    }

    /**
     * 注册中心恢复
     * @throws Exception 异常
     */
    protected void recover() throws Exception{
        // register
        Set<Node> recoverRegistered = new HashSet<>(getRegistered());
        if (!recoverRegistered.isEmpty()){
            if (log.isInfoEnabled())
                log.info("Recover ---->:  register node " + recoverRegistered);
            for (Node node : recoverRegistered) {
                register(node);
            }
        }

        // subscribe
        Map<Node, Set<NotifyListener>> recoverSubscribed = new HashMap<>(this.getSubscribed());
        if (!recoverSubscribed.isEmpty()){
            if (log.isInfoEnabled()){
                log.info("Recover ---->:  subscribe node" + recoverSubscribed.keySet());
            }
            for (Map.Entry<Node, Set<NotifyListener>>  entry: recoverSubscribed.entrySet()) {
                Node node = entry.getKey();
                for (NotifyListener  listener : entry.getValue()) {
                    subscribe(node, listener);
                }
            }
        }
    }
}





























