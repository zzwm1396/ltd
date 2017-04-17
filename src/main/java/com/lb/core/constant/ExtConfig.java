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

    /**
     * jvm监控关闭
     */
    String M_STAT_REPORTER_CLOSED  = "monitorStatReporterClosed";

    /**
     * 加载一致性
     */
    String LOADBALANCE = "loadBalance";

    /**
     * 各节点monitor汇报数据间隔
     */
    String LTD_MONITOR_REPORT_INTERVAL = "ltd.monitor.report.interval";

    /**
     *  各个节点选择连接Monitor的负载均衡算法
     */
    String MONITOR_SELECT_LOADBALANCE = "monitor.select.loadbalance";

    /**
     *  Java 编译器, 可选值 jdk, javassist, 默认 javassist
     */
    String COMPILER = "java.compiler";


    /**
     * 远程通讯序列化方式, 可选值 fastjson, hessian2, java, 默认fastjson
     */
    String REMOTING_SERIALIZABLE_DFT = "ltd.remoting.serializable.default";

    /**
     * 各种节点的 Http Cmd 端口
     */
    String HTTP_CMD_PORT = "ltd.http.cmd.port";

    /**
     * 选择jobDispatch负载均衡算法
     */
    String JOB_DISPATCH_SELECT_LOADBALANCE = "jobDispatch.select.loadbalance";


    /**
     *  远程通讯请求处理线程数量, 默认 32 + AVAILABLE_PROCESSOR * 5
     */
    String PROCESSOR_THREAD = "ltd.job.processor.thread";
}
