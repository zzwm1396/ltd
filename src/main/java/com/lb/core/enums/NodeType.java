package com.lb.core.enums;

/**
 * Created by libo on 2017/4/7.
 */
public enum NodeType {
    // job tracker
    JOB_TRACKER,
    // task tracker
    TASK_TRACKER,
    // client
    JOB_CLIENT,
    // monitor
    MONITOR,

    BACKEND;

    public static NodeType convert(String value){
        if (value == null || "".equals(value))
            return null;
        return NodeType.valueOf(value);
    }
}
