package com.lb.core.cluster.Node;

import com.alibaba.fastjson.JSON;
import com.lb.core.registry.NodeRegistryUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 节点信息
 * Created by libo on 2017/4/14.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Node {

    /**
     * 节点是否可用
     */
    private boolean available = true;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * ip
     */
    private String ip;

    /**
     * 节点端口
     */
    private Integer port = 0;

    /**
     * hostName
     */
    private String hostName;

    /**
     * 节点创建时间
     */
    private Long createTime;

    /**
     * 节点运行线程数
     */
    private Integer threads;

    /**
     * 节点唯一标识
     */
    private String identity;

    /**
     * 命令端口
     */
    private Integer httpCmdPort;

    /**
     * 节点类型
     */
    private NodeType nodeType;

    /**
     * 应用节点组
     */
    private String nodeGroup;

    // 自己关注的节点类型
    private List<NodeType> listenNodeTypes;

    /**
     * 节点转变为注册中心可识别信息
     */
    private String fullString;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String toFullString() {
        if (fullString == null) {
            fullString = NodeRegistryUtils.getFullPath(this);
        }
        return fullString;
    }
}
