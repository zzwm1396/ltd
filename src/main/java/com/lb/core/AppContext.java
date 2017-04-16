package com.lb.core;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.SubscribedNodeManager;
import com.lb.core.command.CommandBodyWrapper;
import com.lb.core.ec.EventCenter;
import com.lb.core.registry.RegistryStatMonitor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用来存储 程序的数据
 * Created by libo on 2017/4/14.
 */
@Getter
@Setter
public class AppContext {

    // 节点配置信息
    private Config config;

    // 事件中心
    private EventCenter eventCenter;

    // 节点管理
    private SubscribedNodeManager subscribedNodeManager;

    // 节点通信CommandBody 包装器

    private CommandBodyWrapper commandBodyWrapper;

    // 注册中心状态监控
    private RegistryStatMonitor registryStatMonitor;


}
