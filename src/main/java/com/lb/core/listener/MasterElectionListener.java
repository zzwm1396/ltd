package com.lb.core.listener;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node;
import com.lb.core.commons.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libo on 2017/4/13.
 */
public class MasterElectionListener implements NodeChangeListener {

    private AppContext appContext;

    public MasterElectionListener(AppContext appContext){
        this.appContext = appContext;
    }

    @Override
    public void addNodes(List<Node> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        // 只需要和当前节点相同的节点类型和组
        List<Node> groupNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (isSameGroup(node)) {
                groupNodes.add(node);
            }
        }
        if (groupNodes.size() > 0) {
            appContext.getMasterElector().addNodes(groupNodes);
        }
    }

    @Override
    public void removeNodes(List<Node> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        // 只需要和当前节点相同的节点类型和组
        List<Node> groupNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (isSameGroup(node)) {
                groupNodes.add(node);
            }
        }
        if (groupNodes.size() > 0) {
            appContext.getMasterElector().removeNode(groupNodes);
        }
    }

    /**
     * 是否和当前节点是相同的GROUP
     *
     * @param node
     * @return
     */
    private boolean isSameGroup(Node node) {
        return node.getNodeType().equals(appContext.getConfig().getNodeType())
                && node.getGroup().equals(appContext.getConfig().getNodeGroup());
    }

}
