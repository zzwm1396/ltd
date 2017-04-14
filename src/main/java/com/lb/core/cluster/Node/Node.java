package com.lb.core.cluster.Node;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点信息
 * Created by libo on 2017/4/14.
 */
public class Node {

    // 节点是否可用
    @Getter
    @Setter
    private boolean available = true;

    // 集群名称
    @Setter
    @Getter
    private String clusterName;

    // 节点ip
    @Getter
    @Setter
    private String ip;

    // 节点端口
    @Setter
    @Getter
    private Integer port = 0;

    // hostName
    @Getter
    @Setter
    private String hostName;

    // 节点创建时间
    @Setter
    @Getter
    private Long createTime;

    // 节点运行线程数
    @Getter
    @Setter
    private Integer threads;

    // 节点唯一标识
    @Setter
    @Getter
    private String identity;

    // 命令端口
    @Getter
    @Setter
    private Integer httpCmdPort;

    // 节点类型
    @Setter
    @Getter
    private NodeType nodeType;

    // 应用节点组
    @Getter
    @Setter
    private String nodeGroup;

    @Override
    public String toString() {
        return "Node{" +
                "available=" + available +
                ", clusterName='" + clusterName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", hostName='" + hostName + '\'' +
                ", createTime=" + createTime +
                ", threads=" + threads +
                ", identity='" + identity + '\'' +
                ", httpCmdPort=" + httpCmdPort +
                ", nodeType=" + nodeType +
                ", nodeGroup='" + nodeGroup + '\'' +
                '}';
    }
}
