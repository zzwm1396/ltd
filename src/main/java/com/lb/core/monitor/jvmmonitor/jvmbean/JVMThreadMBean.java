package com.lb.core.monitor.jvmmonitor.jvmbean;

import java.math.BigDecimal;

/**
 * JVMThreadMBean
 * Created by libo on 2017/4/17.
 */
public interface JVMThreadMBean {
    int getDaemonThreadCount();

    int getThreadCount();

    long getTotalStartedThreadCount();

    int getDeadLockedThreadCount();

    BigDecimal getProcessCpuTimeRate();
}
