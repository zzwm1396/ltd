package com.lb.core.monitor.jvmmonitor;

import java.math.BigDecimal;

/**
 * Created by libo on 2017/4/11.
 */
public interface JVMThreadMBean {
    int getDaemonThreadCount();

    int getThreadCount();

    long getTotalStartedThreadCount();

    int getDeadLockedThreadCount();

    BigDecimal getProcessCpuTimeRate();
}
