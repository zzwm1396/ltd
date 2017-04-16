package com.lb.core.constant;

/**
 * 额外的参数信息
 * Created by libo on 2017/4/15.
 */
public interface ExtConfig {
    /**
     * 注册中心失败事件重试事件
     */
    String REGISTRY_RETRY_PERIOD_KEY = "retry.period";

    /**
     * 所有端: 链接zk的客户端, 可选值 zkClient, curator, ltd 默认 zklient
     */
    String ZK_CLIENT_KEY = "zk.client";
}
