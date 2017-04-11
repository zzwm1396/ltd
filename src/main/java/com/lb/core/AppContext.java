package com.lb.core;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.MasterElector;
import com.lb.core.cluster.SubscribedNodeManager;
import com.lb.core.command.CommandBodyWrapper;
import com.lb.core.ec.EventCenter;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by libo on 2017/4/7.
 */
public abstract class AppContext {

    // 事件中心
    @Getter
    @Setter
    private EventCenter eventCenter;

    // 节点配置信息
    @Setter
    @Getter
    private Config  config;

    // 节点管理
    @Setter
    @Getter
    private SubscribedNodeManager subscribedNodeManager;

    // 节点选举者
    @Getter
    @Setter
    private MasterElector masterElector;

    // 节点通信CommandBody包装器
    @Setter
    @Getter
    private CommandBodyWrapper commandBodyWrapper;


}
