package com.lb.core.monitor.jvmmonitor;

import java.util.Date;

/**
 * Created by libo on 2017/4/11.
 */
public interface JVMInfoMBean {
    Date getStartTime();

    String getJVM();

    String getJavaVersion();

    String getPID();

    String getInputArguments();

    String getJavaHome();

    String getArch();

    String getOSName();

    String getOSVersion();

    String getJavaSpecificationVersion();

    String getJavaLibraryPath();

    String getFileEncode();

    int getAvailableProcessors();

    int getLoadedClassCount();

    long getTotalLoadedClassCount();

    long getUnloadedClassCount();

    long getTotalCompilationTime();

    String  getHostName();

    String getLocalIp();

}
