package com.lb.core.factory;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.Node;
import com.lb.core.commons.utils.NetUtils;
import com.lb.core.support.SystemClock;

/**
 * 节点工厂
 * Created by libo on 2017/4/11.
 */
public class NodeFactory {
    /**
     * 通过工厂创建节点
     */
    public static <T extends Node> T create(Class<T> cls){
        try{
            return cls.newInstance();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    /**
     * 为节点绑定参数信息
     * @param node 节点
     * @param config 任务节点配置
     */
    public static void build(Node node, Config config){
        node.setCreateTime(SystemClock.now());
        node.setIp(config.getIp());
        node.setHostName(NetUtils.getLocalHostName());
        node.setGroup(config.getNodeGroup());
        node.setThreads(config.getWorkThreads());
        node.setPort(config.getListenPort());
        node.setIdentity(config.getIdentity());
        node.setClusterName(config.getClusterName());
    }
}
