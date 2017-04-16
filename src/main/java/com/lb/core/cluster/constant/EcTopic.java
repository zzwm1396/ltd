package com.lb.core.cluster.constant;

/**
 * 事件信息常量
 * Created by libo on 2017/4/14.
 */
public interface EcTopic {

    /**
     * 添加节点
     */
    String NODE_ADD = "node_add";

    /**
     * 移除节点
     */
    String NODE_REMOVE = "node_remove";

    /**
     * 注册中心可用
     */

    String REGISTRY_AVAILABLE = "REGISTRY_AVAILABLE";
    /**
     * 注册中心不可用
     */
    String REGISTRY_UN_AVAILABLE = "REGISTRY_UN_AVAILABLE";
}
