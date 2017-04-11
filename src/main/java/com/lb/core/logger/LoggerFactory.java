package com.lb.core.logger;

import com.lb.core.logger.support.FailSafeLogger;

import java.io.File;
import java.util.Map;
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

    private static final ConcurrentHashMap<String, FailSafeLogger> LOGGERS = new ConcurrentHashMap<>();

    static {
        String logger = System.getProperty("ltd.logger");
     //   if ("logback".equals(logger))
    }

    private static void setLoggerAdapter(String loggerAdapter){
        if (loggerAdapter != null && loggerAdapter.length() > 0){
       //     setLoggerAdapter(ServiceLoader.load(LoggerAdapter.class, loggerAdapter));
        }
    }

    /**
     * 设置日志输出供给器
     * @param loggerAdapter 日志输出器供给器
     */
    public static void setLoggerAdapter(LoggerAdapter loggerAdapter){
        if(loggerAdapter != null){
            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
            logger.info("using logger: " + loggerAdapter.getClass().getName());
            LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
            for (Map.Entry<String, FailSafeLogger> entry : LOGGERS.entrySet()) {
                entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
            }
        }
    }

    /**
     * 获取日志输出器
     * @param key 分类键
     * @return 日志输出器，后验条件：不返回null
     */
    public static Logger getLogger(Class<?> key){
        FailSafeLogger logger = LOGGERS.get(key.getName());
        if (logger == null){
            LOGGERS.putIfAbsent(key.getName(), new FailSafeLogger(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key.getName());
        }
        return logger;
    }

    /**
     * 获取日志输出器
     * @param key 分类键
     * @return 日志输出器， 后验条件：不返回null
     */
    public static Logger getLogger(String key){
        FailSafeLogger logger = LOGGERS.get(key);
        if (logger == null){
            LOGGERS.putIfAbsent(key, new FailSafeLogger(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key);
        }
        return logger;
    }

    /**
     * 动态设置日志输出级别
     * @param level 日志级别
     */
    public static void setLevel(LoggerLevel level){
        LOGGER_ADAPTER.setLevel(level);
    }

    /**
     * 获取日志级别
     * @return 日志级别
     */
    public static LoggerLevel getLevel(){
        return LOGGER_ADAPTER.getLevel();
    }

    /**
     * 获取日志文件
     * @return 日志文件
     */
    public static File getFile(){
        return LOGGER_ADAPTER.getFile();
    }
}




























