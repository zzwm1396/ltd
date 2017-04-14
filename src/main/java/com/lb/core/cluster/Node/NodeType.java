package com.lb.core.cluster.Node;

/**
 * 节点类型: MasterNode, TaskNode, ClientNode
 * <p>
 *     节点无状态，所有节点之间可以转换，当系统启动后，由系统确定节点类型，之后根据节点类型，执行节点功能
 *     节点类型在系统运行过程中由系统自行控制改变
 * </p>
 * Created by libo on 2017/4/14.
 */
public enum  NodeType {
    MASTER_NODE,
    TASK_NODE,
    CLIENT_NODE
}
