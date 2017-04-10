package com.lb.core.logger;

/**
 * Created by libo on 2017/4/10.
 */
public interface Logger {

    /**
     * 输出跟踪信息
     * @param msg 信息内容
     */
    public void trace(String msg);

    /**
     * 输出跟踪信息
     * @param e 异常信息
     */
    public void trace(Throwable e);

    /**
     * 输出追踪信息
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void trace(String msg, Throwable e);

    /**
     * 输出追踪信息
     * @param format 信息内容
     * @param arguments 参数列表
     */
    public void trace(String format, Object... arguments);

    /**
     * 输出调试信息
     * @param msg 消息内容
     */
    public void debug(String msg);

    /**
     * 输出调试信息
     * @param e 异常信息
     */
    public void debug(Throwable e);

    /**
     * 输出调试信息
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void debug(String msg, Throwable e);

    /**
     * 输出调试信息
     * @param format 信息内容
     * @param arguments 参数列表
     */
    public void debug(String format, Object... arguments);

    /**
     * 输出普通信息
     * @param msg 信息内容
     */
    public void info(String msg);

    /**
     * 输出普通信息
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void info(String msg, Throwable e);

    /**
     * 输出普通信息
     * @param e 异常信息
     */
    public void info(Throwable e);

    /**
     * 输出普通信息
     * @param format 信息内容
     * @param arguments  参数列表
     */
    public void info(String format, Object... arguments);

    /**
     * 输出警告信息
     * @param msg 信息内容
     */
    public void warn(String msg);

    /**
     * 输出警告信息
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void warn(String msg, Throwable e);

    /**
     * 输出警告信息
     * @param e 异常信息
     */
    public void warn(Throwable e);

    /**
     * 输出警告信息
     * @param format 信息内容
     * @param arguments 参数列表
     */
    public void warn(String format, Object... arguments);

    /**
     * 输出错误信息
     * @param msg 信息内容
     */
    public void error(String msg);

    /**
     * 输出错误信息
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void error(String msg, Throwable e);

    /**
     * 输出错误信息
     * @param e 异常信息
     */
    public void error(Throwable e);

    /**
     * 输出错误信息
     * @param format 信息内容
     * @param objects 参数列表
     */
    public void error(String format, Object... objects);

    /**
     * 跟踪信息是否开启
     * @return 是否开启
     */
    public boolean isTraceEnabled();

    /**
     * 普通信息是否开启
     * @return 是否开启
     */
    public boolean isInfoEnabled();

    /**
     * 调试信息是否开启
     * @return 是否开启
     */
    public boolean isDebugEnabled();

    /**
     * 警告信息是否开启
     * @return 是否开启
     */
    public boolean isWarnEnabled();

    /**
     * 错误信息是否开启
     * @return
     */
    public boolean isErrorEnabled();












}
