package com.lb.core.listener;

import com.lb.core.cluster.Node.Node;

/**
 * Master 节点监听器
 * Created by libo on 2017/4/14.
 */
public interface MasterChangeListener {

    /**
     * 节点变化监听
     * @param master master 节点
     * @param isMaster 当前节点是否是master节点
     */
    public void change(Node master, boolean isMaster);
}
