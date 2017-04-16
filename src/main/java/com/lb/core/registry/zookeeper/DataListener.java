package com.lb.core.registry.zookeeper;

/**
 * 节点数据监听
 * Created by libo on 2017/4/15.
 */
public interface DataListener {

    /**
     * 数据变化监听
     * @param dataPath 数据路径
     * @param data 数据信息
     * @throws Exception 异常信息
     */
    void dataChange(String dataPath, Object data) throws Exception;

    /**
     * 数据删除监听
     * @param dataPath 数据路径
     * @throws Exception 异常信息
     */
    void dataDeleted(String dataPath) throws Exception;
}
