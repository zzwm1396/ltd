package com.lb.core.monitor.jvmmonitor;

/**
 * 一些jvm常量信息
 * Created by libo on 2017/4/17.
 */
public interface JVMConstants {
    String JMX_JVM_INFO_NAME = "ccom.lb.core.monitor.jvmmonitor:type=JVMInfo";
    String JMX_JVM_MEMORY_NAME = "com.lb.core.monitor.jvmmonitor:type=JVMMemory";
    String JMX_JVM_GC_NAME = "com.lb.core.monitor.jvmmonitor:type=JVMGC";
    String JMX_JVM_THREAD_NAME = "com.lb.core.monitor.jvmmonitor:type=JVMThread";
}
