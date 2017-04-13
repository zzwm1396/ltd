package com.lb.core.cluster;

import com.lb.core.AppContext;

/**
 * 执行节点
 * <p>
 *     所有节点都是无状态节点，系统启动后自动选举出Master节点
 *     Master节点负责分发调度任务，并监控各个几点的负载情况
 *     Master宕机后，所有任务暂停，重新选举Master,Master节点上的人物重新分配到其他节点执行，Master节点不执行具体的任务
 * </p>
 * Created by libo on 2017/4/13.
 */
public class TrackerNode  < T extends Node, Context extends AppContext>extends AbstractJobNode<T, Context>{
    @Override
    protected void remotingStart() {

    }

    @Override
    protected void remotingStop() {

    }

    @Override
    protected void beforeRemotingStart() {

    }

    @Override
    protected void afterRemotingStart() {

    }

    @Override
    protected void beforeRemotingStop() {

    }

    @Override
    protected void afterRemotingStop() {

    }
}
