package com.lb.core.listener;

import com.lb.core.cluster.Node;

/**
 * 节点变化监听器
 * Created by libo on 2017/4/10.
 */
public interface MasterChangeListener {
    /**
     * 节点变化 监听
     * @param master master节点
     * @param isMaster 表示当前节点是不是master节点
     */
    public void change(Node master, boolean isMaster);
}
