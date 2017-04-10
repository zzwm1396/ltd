package com.lb.core.logger;

import com.lb.core.logger.support.FailSafeLogger;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志输出工厂
 * Created by libo on 2017/4/10.
 */
public class LoggerFactory {
    private LoggerFactory(){
    }

    private static volatile  LoggerAdapter LOGGER_ADAPTER;

    private static final ConcurrentHashMap<String, FailSafeLogger> loggers = new ConcurrentHashMap<>();

    static {
        String logger = System.getProperty("ltd.logger");
     //   if ("logback".equals(logger))
    }

    private static void setLoggerAdapter(String loggerAdapter){
        if (loggerAdapter != null && loggerAdapter.length() > 0){
       //     setLoggerAdapter(ServiceLoader.load(LoggerAdapter.class, loggerAdapter));
        }
    }
}
