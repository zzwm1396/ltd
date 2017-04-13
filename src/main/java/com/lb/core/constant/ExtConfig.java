package com.lb.core.constant;

/**
 * Created by libo on 2017/4/12.
 */
public interface ExtConfig {
    public String M_STAT_REPORTER_CLOSED = "MStatReport.closed";

    /**
     * java 编译器，可选值，jdk, javassist, 默认javassist
     */
    String COMPILER = "java.compiler";

    String HTTP_CMD_PORT = "ltd.http.cmd.port";

    /**
     * 注册中心失败事件重试事件
     */
    String REGISTRY_RETRY_PERIOD_KEY = "retry.period";

    String LOADBALANCE = "loadbalance";


    /**
     *  向monitor汇报数据间隔
     */
    String LTS_MONITOR_REPORT_INTERVAL = "ltd.monitor.report.interval";

}
