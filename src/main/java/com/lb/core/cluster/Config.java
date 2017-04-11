package com.lb.core.cluster;

import com.alibaba.fastjson.JSON;
import com.lb.core.enums.NodeType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务节点配置
 * Created by libo on 2017/4/7.
 */
public class Config implements Serializable {
    private static final long serialVersionUID = 4473661076279230005L;

    // 节点是否可用
    @Setter
    @Getter
    private boolean available = true;

    // 应用节点组
    @Setter
    @Getter
    private String nodeGroup;

    // 唯一标识
    @Setter
    @Getter
    private String identity;

    // 工作线程， 目前只对 TaskTracker 有效
    @Setter
    @Getter
    private int workThreads;

    // 节点类型
    @Setter
    @Getter
    private NodeType nodeType;

    // 注册中心 地址
    @Setter
    @Getter
    private String registryAddress;

    // 远程连接超时时间
    @Setter
    @Getter
    private int invokeTimeOutMillis;

    // 监听端口
    @Setter
    @Getter
    private int listenPort;

    @Setter
    @Getter
    private String ip;

    // 任务信息存储路径
    @Setter
    @Getter
    private String dataPath;

    // 集群名称
    @Setter
    @Getter
    private String clusterName;

    @Setter
    @Getter
    private volatile transient Map<String, Number> numbers;

    @Setter
    @Getter
    private final Map<String, String> parameters = new HashMap<>();

    @Setter
    @Getter
    private final Map<String, Object> internalData = new ConcurrentHashMap<>();


    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getParameter(String key, String defaultValues) {
        String value = parameters.get(key);
        if (value == null) {
            return defaultValues;
        }
        return value;
    }

    public boolean getParameter(String key, boolean defaultValue) {
        String value = getParameter(key);
        if (value == null || value.length() == 0)
            return defaultValue;
        return Boolean.parseBoolean(value);
    }


    @SuppressWarnings("unchecked")
    public <T> T getInternalData(String key, T defaultValue) {
        Object obj = internalData.get(key);
        if (obj == null) {
            return defaultValue;
        }
        return (T) obj;
    }

    public <T> T getInternalData(String key) {
        return getInternalData(key, null);
    }

    public void setInternalData(String key, Object value) {

        internalData.put(key, value);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}











