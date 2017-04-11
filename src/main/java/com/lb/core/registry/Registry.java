package com.lb.core.registry;

import com.lb.core.cluster.Node;

/**
 * 节点注册接口
 * Created by libo on 2017/4/7.
 */
public interface Registry {

    /**
     * 节点注册
     * @param node 节点
     */
    void register(Node node);

    /**
     * 取消注册
     * @param node 节点
     */
    void unregister(Node node);

    /**
     * 监听节点
     * @param node 节点
     * @param listener 监听器
     */
    void subscribe(Node node, NotifyListener listener);

    /**
     * 取消监听节点
     * @param node 节点
     * @param listener  监听器
     */
    void unSubscribe(Node node, NotifyListener listener);

    /**
     * 销毁
     */
    void destroy();
}
