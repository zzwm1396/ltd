package com.lb.core.cluster;

import com.alibaba.fastjson.JSON;
import com.lb.core.domain.Job;
import com.lb.core.enums.NodeType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点
 * Created by libo on 2017/4/7.
 */
public class Node {

    // 是否可用
    @Getter
    @Setter
    private boolean available = true;

    // 集群名称
    @Getter
    @Setter
    private String clusterName;

    // 节点类型
    @Getter
    @Setter
    private NodeType nodeType;

    // 节点ip
    @Getter
    @Setter
    private String ip;

    // 节点端口
    @Getter
    @Setter
    private Integer port = 0;

    // host名称
    @Getter
    @Setter
    private String hostName;

    // 集群名称
    @Getter
    @Setter
    private String group;

    // 创建时间
    @Getter
    @Setter
    private Long createTime;

    //线程个数
    @Getter
    @Setter
    private Integer threads;

    //唯一标识
    @Getter
    @Setter
    private String identity;

    // 命令端口
    @Getter
    @Setter
    private Integer httpCmdPort;

    // 自己关注的节点类型
    @Getter
    @Setter
    private List<NodeType> listNodeType;

    @Getter
    @Setter
    private String fullString;

    @Setter
    @Getter
    private Job job;

    /**
     * 添加关注节点类型
     * @param nodeType 节点类型
     */
    public void addListenNodeType(NodeType nodeType) {
        if (this.listNodeType == null) {
            this.listNodeType = new ArrayList<>();
        }
        this.listNodeType.add(nodeType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Node node = (Node) obj;
        return !(identity != null ? !identity.equals(node.identity) : node.identity != null);
    }

    @Override
    public int hashCode() {
        return identity != null ? identity.hashCode() : 0;
    }

    /**
     *  获取节点地址
     * @return 节点地址信息：如：127.0.0.1:1010
     */
    public String getAddress(){
        return ip + ":" + port;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
