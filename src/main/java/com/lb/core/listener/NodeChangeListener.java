package com.lb.core.listener;

import com.lb.core.cluster.Node;

import java.util.List;

/**
 * 监听节点
 * Created by libo on 2017/4/7.
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
