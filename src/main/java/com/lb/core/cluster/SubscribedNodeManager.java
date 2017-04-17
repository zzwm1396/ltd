package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.commons.concurrent.ConcurrentHashSet;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.commons.utils.ListUtils;
import com.lb.core.ec.EventInfo;
import com.lb.core.listener.NodeChangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点管理
 * <p>
 *     主要用于管理自己关注的节点
 * </p>
 * Created by libo on 2017/4/14.
 */
@Slf4j
public class SubscribedNodeManager implements NodeChangeListener {

    private final ConcurrentHashMap<NodeType, Set<Node>> NODES  = new ConcurrentHashMap<>();

    private AppContext appContext;

    public SubscribedNodeManager(AppContext appContext) {
        this.appContext = appContext;
    }

    private void addNode(Node node){
        Set<Node> nodeSet = NODES.get(node.getNodeType());
        if (CollectionUtils.isEmpty(nodeSet)){
            nodeSet = new ConcurrentHashSet<Node>();
            Set<Node> oldNodeSet = NODES.putIfAbsent(node.getNodeType(), nodeSet);
            if (oldNodeSet != null){
                nodeSet = oldNodeSet;
            }
        }
        nodeSet.add(node);
        EventInfo eventInfo = new EventInfo(EcTopic.NODE_ADD);
        eventInfo.setParam("node", node);
        appContext.getEventCenter().publishSync(eventInfo);
        log.info("Add {}", node);
    }

    private void removeNode(Node delNode){
        Set<Node> nodeSet = NODES.get(delNode.getNodeType());
        if (CollectionUtils.isNotEmpty(nodeSet)){
            for (Node node : nodeSet) {
                if (node.getIdentity().equals(delNode.getIdentity())){
                    nodeSet.remove(node);
                    EventInfo eventInfo = new EventInfo(EcTopic.NODE_REMOVE);
                    eventInfo.setParam("node", node);
                    appContext.getEventCenter().publishSync(eventInfo);
                    log.info("Remove {}", node);
                }
            }
        }
    }

    /**
     * 获取相关节点类型的节点列表
     * @param nodeType 节点类型
     * @return 节点列表
     */
    public List<Node> getNodeList(NodeType nodeType){
        return CollectionUtils.setToList(NODES.get(nodeType));
    }

    /**
     * 获取相同节点类型，相同应用节点组下的节点列表
     * @param nodeType 节点类型
     * @param nodeGroup 应用节点组
     * @return 节点列表
     */
    public List<Node> getNodeList(final NodeType nodeType, final String nodeGroup){
        Set<Node> nodes = NODES.get(nodeType);
        return ListUtils.filter(CollectionUtils.setToList(nodes), new ListUtils.Filter<Node>() {
            @Override
            public boolean filter(Node node) {
                return node.getNodeGroup().equals(nodeGroup);
            }
        });
    }

    @Override
    public void addNodes(List<Node> nodes) {
        if (CollectionUtils.isEmpty(nodes)){
            return;
        }
        for (Node node : nodes) {
            addNode(node);
        }
    }

    @Override
    public void removeNodes(List<Node> nodes) {
        if (CollectionUtils.isEmpty(nodes)){
            return;
        }
        for (Node node : nodes) {
            removeNode(node);
        }
    }
}
