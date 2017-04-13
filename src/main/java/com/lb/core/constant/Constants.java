package com.lb.core.constant;

/**
 * 配置常量
 * Created by libo on 2017/4/10.
 */
public interface Constants {

    // 可用的处理器个数
    int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();

    String OS_NAME = System.getProperty("os.name");

    String USER_HOME = System.getProperty("user.home");

    String LINE_SEPARATOR = System.getProperty("line.separator");

    // 默认监听接口
    int JOB_TRACKER_DEFAULT_LISTEN_PORT = 35001;

    String DEFAULT_CLUSTER_NAME = "defaultCluster";

    String CHARSET = "utf-8";

    int DEFAULT_TIMEOUT = 1000;

    int DEFAULT_SESSION_TIMEOUT = 60 * 1000;

    String REGISTER = "register";

    String UNREGISTER = "unregister";

    int DEFAULT_BUFFER_SIZE = 16 * 1024 * 1024;

    /**
     * 重试周期
     */
    int DEFAULT_REGISTRY_RETRY_PERIOD = 5 * 1000;



}
