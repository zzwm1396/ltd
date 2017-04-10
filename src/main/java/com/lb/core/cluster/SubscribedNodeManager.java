package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.commons.utils.ListUtils;
import com.lb.core.constant.EcTopic;
import com.lb.core.ec.EventInfo;
import com.lb.core.enums.NodeType;
import com.lb.core.listener.NodeChangeListener;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 节点管理(主要用于管理自己关注的节点)
 * Created by libo on 2017/4/7.
 */
public class SubscribedNodeManager implements NodeChangeListener {

    private static final Logger logger = Logger.getLogger(SubscribedNodeManager.class);

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
        EventInfo eventInfo = new EventInfo(EcTopic.NODE_ADD);
        eventInfo.setParam("node", node);
        appContext.getEventCenter().publishSync(eventInfo);

    }

    /**
     * 获取节点列表
     * @param nodeType 节点类型
     * @param nodeGroup 集群
     */
    public  List<Node> getNodeList(final NodeType nodeType, final String nodeGroup){
        Set<Node> ns = nodes.get(nodeType);

        return ListUtils.filter(CollectionUtils.setToList(ns), new ListUtils.Filter<Node>() {
            @Override
            public boolean filter(Node node) {
                return node.getGroup().equals(nodeGroup);
            }
        });
    }

    private void removeNode(Node delNode){
        Set<Node> nodeSet = nodes.get(delNode.getNodeType());

        if (!nodeSet.isEmpty()){
            for (Node node : nodeSet) {
                if (node.getIdentity().equals(delNode.getIdentity())){
                    nodeSet.remove(node);
                    EventInfo eventInfo  = new EventInfo(EcTopic.NODE_REMOVE);
                    eventInfo.setParam("node", node);
                    appContext.getEventCenter().publishSync(eventInfo);
                }
            }
        }
    }

    public List<Node> getNodeList(NodeType nodeType){
        return CollectionUtils.setToList(nodes.get(nodeType));
    }

    @Override
    public void addNodes(List<Node> nodes) {
        if (nodes.isEmpty())
            return;
        for (Node node : nodes) {
            addNode(node);
        }
    }

    @Override
    public void removeNodes(List<Node> nodes) {
        if (nodes.isEmpty())
            return;
        for (Node node : nodes) {
            removeNode(node);
        }
    }
}
