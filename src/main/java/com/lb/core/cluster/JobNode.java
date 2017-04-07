package com.lb.core.cluster;

/**
 * Created by libo on 2017/4/7.
 */
public interface JobNode {
    /**
     * 启动节点
     */
    void start();

    /**
     * 停止节点
     */
    void stop();

    /**
     * 销毁节点
     */
    void destroy();
}
