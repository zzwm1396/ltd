package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.commons.utils.ListUtils;
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

    public  List<Node> getNodeList(final NodeType nodeType, final String nodeGroup){
        Set<Node> ns = nodes.get(nodeType);

        return ListUtils.filter(CollectionUtils.setToList(ns), new ListUtils.Filter<Node>() {
            @Override
            public boolean filter(Node node) {
                return node.getGroup().equals(nodeGroup);
            }
        });
    }
    @Override
    public void addNodes(List<Node> nodes) {

    }

    @Override
    public void removeNodes(List<Node> nodes) {

    }
}
