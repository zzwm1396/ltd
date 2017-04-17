package com.lb.core.constant;

/**
 * 一些常量配置信息
 * Created by libo on 2017/4/15.
 */
public interface Constants {
    /**
     * 重试周期
     */
    int DEFAULT_REGISTRY_RETRY_PERIOD = 5 * 1000;

    /**
     * 可用的处理器个数
     */
    int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();
}
