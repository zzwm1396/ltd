package com.lb.core.monitor.jvmmonitor;

import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.monitor.jvmmonitor.jvmbean.JVMGC;
import com.lb.core.monitor.jvmmonitor.jvmbean.JVMInfo;
import com.lb.core.monitor.jvmmonitor.jvmbean.JVMMemory;
import com.lb.core.monitor.jvmmonitor.jvmbean.JVMThread;
import com.lb.core.support.CrossClassLoader;
import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * jvm监控信息
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class JVMMonitor {

    private static final MBeanServer MBEAN_SERVER = ManagementFactory.getPlatformMBeanServer();
    private static final AtomicBoolean start = new AtomicBoolean(false);

    private static AtomicLong refCount;

    static {
        String className = JVMMonitor.class.getName() + "$JVMMonitorReferenceCount";
        try {
            Class clazz = CrossClassLoader.loadClass(className);
            Field refCountField = clazz.getDeclaredField("REF_COUNT");
            refCountField.setAccessible(true);
            refCount = (AtomicLong) refCountField.get(null);
        } catch (Throwable t) {
            log.warn("load " + className + " error", t);
            refCount = new AtomicLong(0);
        }
    }

    private static AtomicLong getRefCount() {
        return refCount;
    }

    private final static Map<String, Object> MONITOR_MAP = new HashMap<String, Object>();

    public static void start() {
        getRefCount().incrementAndGet();
        if (start.compareAndSet(false, true)) {
            if (CollectionUtils.isEmpty(MONITOR_MAP)) {
                MONITOR_MAP.put(JVMConstants.JMX_JVM_INFO_NAME, JVMInfo.getInstance());
                MONITOR_MAP.put(JVMConstants.JMX_JVM_MEMORY_NAME, JVMMemory.getInstance());
                MONITOR_MAP.put(JVMConstants.JMX_JVM_GC_NAME, JVMGC.getInstance());
                MONITOR_MAP.put(JVMConstants.JMX_JVM_THREAD_NAME, JVMThread.getInstance());
            }
            try {
                for (Map.Entry<String, Object> entry : MONITOR_MAP.entrySet()) {
                    ObjectName objectName = new ObjectName(entry.getKey());
                    if (!MBEAN_SERVER.isRegistered(objectName)) {
                        MBEAN_SERVER.registerMBean(entry.getValue(), objectName);
                    }
                }
                log.info("Start JVMMonitor succeed ");
            } catch (Exception e) {
                log.error("Start JVMMonitor error ", e);
            }
        }
    }

    public static void stop() {
        getRefCount().decrementAndGet();
        // 只有启动了,并且引用为0的时候才unregister
        if (start.compareAndSet(true, false) && getRefCount().get() == 0) {
            for (Map.Entry<String, Object> entry : MONITOR_MAP.entrySet()) {
                try {
                    ObjectName objectName = new ObjectName(entry.getKey());
                    if (MBEAN_SERVER.isRegistered(objectName)) {
                        MBEAN_SERVER.unregisterMBean(objectName);
                    }
                } catch (Exception e) {
                    log.error("Stop JVMMonitor {} error", entry.getKey(), e);
                }
            }
            log.info("Stop JVMMonitor succeed ");
        }
    }

    public static Map<String, Object> getAttribute(String objectName, List<String> attributeNames) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            for (String attributeName : attributeNames) {
                try {
                    Object value = MBEAN_SERVER.getAttribute(new ObjectName(objectName), attributeName);
                    result.put(attributeName, value);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            log.error("get Attribute error, objectName=" + objectName + ", attributeName=" + attributeNames, e);
        }
        return result;
    }

    public static Object getAttribute(String objectName, String attributeName) {
        try {
            return MBEAN_SERVER.getAttribute(new ObjectName(objectName), attributeName);
        } catch (Exception e) {
            log.error("get Attribute error, objectName=" + objectName + ", attributeName=" + attributeName, e);
        }
        return null;
    }

    private static class JVMMonitorReferenceCount {
        // 这里必须为static, 保证所有实例引用的都是一个REF_COUNT
        private static final AtomicLong REF_COUNT = new AtomicLong(0);
    }
}
