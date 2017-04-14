package com.lb.core;

import com.lb.core.cluster.Config;
import com.lb.core.cluster.SubscribedNodeManager;
import com.lb.core.command.CommandBodyWrapper;
import com.lb.core.ec.EventCenter;
import lombok.Getter;
import lombok.Setter;

/**
 * 用来存储 程序的数据
 * Created by libo on 2017/4/14.
 */
public class AppContext {

    // 节点配置信息
    @Setter
    @Getter
    private Config config;

    // 事件中心
    @Setter
    @Getter
    private EventCenter eventCenter;

    // 节点管理
    @Getter
    @Setter
    private SubscribedNodeManager subscribedNodeManager;

    // 节点通信CommandBody 包装器
    @Getter
    @Setter
    private CommandBodyWrapper commandBodyWrapper;


}
