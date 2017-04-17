package com.lb.core.factory;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.cluster.constant.Constants;
import com.lb.core.commons.utils.DateUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 节点配置信息工厂
 * Created by libo on 2017/4/17.
 */
public class NodeConfigFactory {
    private static final AtomicInteger SEQ = new AtomicInteger(0);

    public static Config getDefaultConfig() {
        Config config = new Config();
        config.setWorkThreads(64);
        config.setNodeGroup("lts");
        config.setRegistryAddress("zookeeper://127.0.0.1:2181");
        config.setInvokeTimeOutMillis(1000 * 60);
        config.setDataPath(Constants.USER_HOME);
        config.setClusterName(Constants.DEFAULT_CLUSTER_NAME);
        return config;
    }

    public static void buildIdentity(Config config) {
        String sb = getNodeTypeShort(config.getNodeType()) +
                "_" +
                config.getIp() +
                "_" +
                getPid() +
                "_" +
                DateUtils.format(new Date(), "HH-mm-ss.SSS")
                + "_" + SEQ.incrementAndGet();
        config.setIdentity(sb);
    }

    private static String getNodeTypeShort(NodeType nodeType) {
        switch (nodeType) {
            case MASTER_NODE:
                return "MS";
            case CLIENT_NODE:
                return "CL";
            case TASK_NODE:
                return "TA";
            case MONITOR:
                return "MO";
        }
        throw new IllegalArgumentException();
    }

    private static Integer getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        int index = name.indexOf("@");
        if (index != -1) {
            return Integer.parseInt(name.substring(0, index));
        }
        return 0;
    }
}
