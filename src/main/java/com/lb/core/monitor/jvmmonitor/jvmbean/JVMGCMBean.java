package com.lb.core.monitor.jvmmonitor.jvmbean;

/**
 * Created by libo on 2017/4/17.
 */
public interface JVMGCMBean {
    long getYoungGCCollectionCount();

    long getYoungGCCollectionTime();

    long getFullGCCollectionCount();

    long getFullGCCollectionTime();

    // 下面的数字是做过差计算的,启动后的第二次开始才能做差值
    long getSpanYoungGCCollectionCount();

    long getSpanYoungGCCollectionTime();

    long getSpanFullGCCollectionCount();

    long getSpanFullGCCollectionTime();

}
