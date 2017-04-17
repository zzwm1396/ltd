package com.lb.core.listener;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;
import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.ec.EventInfo;

import java.util.List;

/**
 * 用来监听自己的节点信息变化
 * Created by libo on 2017/4/17.
 */
public class SelfChangeListener  implements NodeChangeListener{

    private Config config;
    private AppContext appContext;

    public SelfChangeListener(AppContext appContext) {
        this.config = appContext.getConfig();
        this.appContext = appContext;
    }


    private void change(Node node) {
        if (node.getIdentity().equals(config.getIdentity())) {
            // 是当前节点, 看看节点配置是否发生变化
            // 1. 看 threads 有没有改变 , 目前只有 TASK_NODE 对 threads起作用
            if (node.getNodeType().equals(NodeType.TASK_NODE)
                    && (node.getThreads() != config.getWorkThreads())) {
                config.setWorkThreads(node.getThreads());
                appContext.getEventCenter().publishAsync(new EventInfo(EcTopic.WORK_THREAD_CHANGE));
            }

            // 2. 看 available 有没有改变
            if (node.isAvailable() != config.isAvailable()) {
                String topic = node.isAvailable() ? EcTopic.NODE_ENABLE : EcTopic.NODE_DISABLE;
                config.setAvailable(node.isAvailable());
                appContext.getEventCenter().publishAsync(new EventInfo(topic));
            }
        }
    }

    @Override
    public void addNodes(List<Node> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        for (Node node : nodes) {
            change(node);
        }
    }

    @Override
    public void removeNodes(List<Node> nodes) {

    }
}
