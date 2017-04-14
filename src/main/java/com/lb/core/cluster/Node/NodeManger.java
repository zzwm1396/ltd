package com.lb.core.cluster.Node;

/**
 * 节点管理接口
 * Created by libo on 2017/4/14.
 */
public interface NodeManger {

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
