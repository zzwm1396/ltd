package com.lb.core.registry.base;

import com.lb.core.cluster.Node.Node;

import java.util.List;

/**
 * 监听通知接口
 * Created by libo on 2017/4/15.
 */
public interface NotifyListener {

    /**
     * 通知客户端方法
     * @param event 事件类型
     * @param nodes 节点列表
     */
    void notify(NotifyEvent event, List<Node> nodes);
}
