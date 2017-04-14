package com.lb.core.listener;

import com.lb.core.cluster.Node.Node;

import java.util.List;

/**
 * 节点变化监听接口
 * Created by libo on 2017/4/14.
 */
public interface NodeChangeListener {

    /**
     * 添加节点
     * @param nodes 节点列表
     */
    public void addNodes(List<Node> nodes);

    /**
     * 移除节点
     * @param nodes 节点列表
     */
    public void removeNodes(List<Node> nodes);
}
