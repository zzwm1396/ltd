package com.lb.core.factory;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.Node.Node;
import com.lb.core.commons.utils.NetUtils;
import com.lb.core.exception.LtdRuntimeException;
import com.lb.core.support.SystemClock;

/**
 * 节点工厂
 * Created by libo on 2017/4/17.
 */
public class NodeFactory {

    /**
     * 创建节点
     * @param clazz 节点类
     * @return 具体的节点
     */
    public static <T extends Node> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new LtdRuntimeException("Create Node error: clazz=" + clazz, e);
        }
    }

    /**
     * 为节点绑定配置信息
     * @param node
     * @param config
     */
    public static void build(Node node, Config config) {
        node.setCreateTime(SystemClock.now());
        node.setIp(config.getIp());
        node.setHostName(NetUtils.getLocalHostName());
        node.setNodeGroup(config.getNodeGroup());
        node.setThreads(config.getWorkThreads());
        node.setPort(config.getListenPort());
        node.setIdentity(config.getIdentity());
        node.setClusterName(config.getClusterName());
    }
}
