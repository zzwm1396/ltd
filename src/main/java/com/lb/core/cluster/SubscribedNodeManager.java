package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.enums.NodeType;
import com.lb.core.listener.NodeChangeListener;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by libo on 2017/4/7.
 */
public class SubscribedNodeManager implements NodeChangeListener {


    private final ConcurrentHashMap<NodeType, Set<Node>> nodes = new ConcurrentHashMap<>();

    private AppContext appContext;

    public SubscribedNodeManager(AppContext appContext){
        this.appContext = appContext;
    }

    private void addNode(Node node){
        Set<Node> nodeSet = nodes.get(node.getNodeType());
        if (nodeSet.isEmpty()){
            nodeSet = new ConcurrentSkipListSet<>();
            Set<Node> oldNodeList = nodes.putIfAbsent(node.getNodeType(), nodeSet);
            if (oldNodeList != null)
                nodeSet = oldNodeList;
        }
        nodeSet.add(node);
    }

    @Override
    public void addNodes(List<Node> nodes) {

    }

    @Override
    public void removeNodes(List<Node> nodes) {

    }
}
