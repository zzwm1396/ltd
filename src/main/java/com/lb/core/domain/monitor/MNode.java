package com.lb.core.domain.monitor;

import com.lb.core.cluster.Node.NodeType;
import lombok.Getter;
import lombok.Setter;

/**
 * MNode
 * Created by libo on 2017/4/17.
 */
@Getter
@Setter
public class MNode {
    private NodeType nodeType;
    /**
     * NodeGroup
     */
    private String nodeGroup;
    /**
     * TaskTracker 节点标识
     */
    private String identity;
}
