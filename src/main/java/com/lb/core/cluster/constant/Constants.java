package com.lb.core.cluster.constant;

import java.util.regex.Pattern;

/**
 * 一些配置常量
 * Created by libo on 2017/4/14.
 */
public interface Constants {

    /**
     * 可用的处理器个数
     */
    int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();

    /**
     * 匹配逗号，包含逗号前后有任意多空白字符的情况
     */
    Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

    /**
     * 主机名称
     */
    String USER_HOME = System.getProperty("user.home");

    /**
     *  默认集群名字
     */
    String DEFAULT_CLUSTER_NAME = "defaultCluster";

    /**
     * adaptive 标识
     */
    String ADAPTIVE = "adaptive";

    int DEFAULT_PROCESSOR_THREAD = 32 + AVAILABLE_PROCESSOR * 5;
}
