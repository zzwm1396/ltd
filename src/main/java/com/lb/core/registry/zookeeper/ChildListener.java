package com.lb.core.registry.zookeeper;

import java.util.List;

/**
 * 子节点监听
 * Created by libo on 2017/4/15.
 */
public interface ChildListener {

    /**
     * 子节点变化监听方法
     * @param path 路径
     * @param children 子节点集
     */
    void childChanged(String path, List<String> children);
}
