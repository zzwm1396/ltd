package com.lb.core.registry.base;

import com.lb.core.cluster.Node.Node;

/**
 * Created by libo on 2017/4/15.
 */
public interface Registry {
    /**
     * 节点注册
     */
    void register(Node node);

    /**
     * 节点 取消注册
     */
    void unRegister(Node node);

    /**
     * 监听节点
     */
    void subscribe(Node node, NotifyListener listener);

    /**
     * 取消监听节点
     */
    void unSubscribe(Node node, NotifyListener listener);

    /**
     * 销毁
     */
    void destroy();
}
