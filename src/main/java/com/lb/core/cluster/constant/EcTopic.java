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

    /**
     *  Master 节点改变了
     */
    String MASTER_CHANGED = "MASTER_CHANGED";

    /**
     *  工作线程变化
     */
    String WORK_THREAD_CHANGE = "WORK_THREAD_CHANGE";

    /**
     * 节点启用
     */
    String NODE_ENABLE = "NODE_ENABLE";

    /**
     * 节点禁用
     */
    String NODE_DISABLE = "NODE_DISABLE";

    /**
     * 停掉节点
     */
    String NODE_SHUT_DOWN = "NODE_SHUT_DOWN";


    /**
     *  没有可用的JOB_DISPATCH了
     */
    String NO_JOB_DISPATCH_AVAILABLE = "NO_JOB_DISPATCH_AVAILABLE";

    /**
     * 任务调度节点可用
     */
    String JOB_DISPATCH_AVAILABLE = "JOB_DISPATCH_AVAILABLE";

}
