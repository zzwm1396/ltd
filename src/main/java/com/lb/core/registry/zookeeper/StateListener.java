package com.lb.core.registry.zookeeper;

/**
 * 状态监听
 * Created by libo on 2017/4/15.
 */
public interface StateListener {

    /**
     * 失去连接
     */
    int DISCONNECTED = 0;

    /**
     * 正常连接
     */
    int CONNECTED = 1;

    /**
     * 重连接
     */
    int RECONNECTED = 2;

    /**
     * 状态改变
     * @param connected 连接状态
     */
    void stateChanged(int connected);
}
