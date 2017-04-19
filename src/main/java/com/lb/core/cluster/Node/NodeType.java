package com.lb.core.cluster.Node;

/**
 * 节点类型:DispatchNode, TaskNode, ClientNode
 * <p>
 *     1>节点无状态，所有节点之间可以转换，当系统启动后，由系统确定节点类型，之后根据节点类型，执行节点功能
 *     2>节点类型在系统运行过程中由系统自行控制改变,也可以通过人工控制改变
 *     3>DISPATCH_NODE节点负责任务调度及集群压力监控(等同于master节点)
 *     4>MASTER节点根据压力情况及集群中的节点数来确定是否需要单独或多个client节点，
 *       节点改变后，相关节点参数重新配置，然后节点重新启动
 * </p>
 * Created by libo on 2017/4/14.
 */
public enum  NodeType {

    /**
     * 任务调度节点
     */
    DISPATCH_NODE,

    /**
     * 任务执行节点
     */
    TASK_NODE,

    /**
     * client节点
     */
    CLIENT_NODE,

    /**
     * MONITOR节点
     */
    MONITOR

}
