package com.lb.core.cluster;

import com.lb.core.cluster.Node.NodeType;
import com.lb.core.cluster.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务节点配置
 * Created by libo on 2017/4/14.
 */
public class Config implements Serializable {

    private static final long serialVersionUID = 4473661076279230005L;

    // 节点是否可用
    @Getter
    @Setter
    private boolean available = true;

    // 应用节点组
    @Setter
    @Getter
    private String nodeGroup;

    // 唯一标识
    @Setter
    @Getter
    private String identity;

    // 工作线程数
    @Getter
    @Setter
    private int workThreads;

    // 节点类型
    @Setter
    @Getter
    private NodeType nodeType;

    // 节点注册中心地址
    @Getter
    @Setter
    private String registryAddress;

    // 远程连接超时时间
    @Setter
    @Getter
    private int invokeTimeOutMillis;

    // 监听接口
    @Getter
    @Setter
    private int listenPort;

    // ip
    @Setter
    @Getter
    private String ip;

    // 集群名称
    @Getter
    @Setter
    private String clusterName;

    // 参数信息
    @Setter
    @Getter
    private final Map<String, String> parameters = new HashMap<>();

    // 内部使用，保证数字类型参数信息是最新的
    @Getter
    @Setter
    private volatile transient Map<String, Number> numbers;

    private String getParameter(String key) {
        return parameters.get(key);
    }

    public String getParameter(String key, String defaultValue) {
        String value = parameters.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public boolean getParameter(String key, boolean defaultValue) {
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public int getParameter(String key, int defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.intValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        int i = Integer.parseInt(value);
        getNumbers().put(key, i);
        return i;
    }

    public String[] getParameter(String key, String[] defaultValue) {
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return Constants.COMMA_SPLIT_PATTERN.split(value);
    }

    public double getParameter(String key, double defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.doubleValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        double d = Double.parseDouble(value);
        getNumbers().put(key, d);
        return d;
    }

    public float getParameter(String key, float defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.floatValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        float f = Float.parseFloat(value);
        getNumbers().put(key, f);
        return f;
    }

    public long getParameter(String key, long defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.longValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        long l = Long.parseLong(value);
        getNumbers().put(key, l);
        return l;
    }

    public short getParameter(String key, short defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.shortValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        short s = Short.parseShort(value);
        getNumbers().put(key, s);
        return s;
    }

    public byte getParameter(String key, byte defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.byteValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        byte b = Byte.parseByte(value);
        getNumbers().put(key, b);
        return b;
    }
}
























