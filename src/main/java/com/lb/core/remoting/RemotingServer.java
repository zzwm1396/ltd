package com.lb.core.remoting;

import com.lb.core.remoting.exception.RemotingException;
import com.lb.core.remoting.exception.RemotingSendRequestException;
import com.lb.core.remoting.exception.RemotingTimeoutException;
import com.lb.core.remoting.exception.RemotingTooMuchRequestException;
import org.apache.zookeeper.AsyncCallback;

import java.util.concurrent.ExecutorService;

/**
 * 远程通信   server接口
 * Created by libo on 2017/4/17.
 */
public interface RemotingServer {
    void start() throws RemotingException;


    /**
     * 注册请求处理器，ExecutorService必须要对应一个队列大小有限制的阻塞队列，防止OOM
     */
    void registerProcessor(final int requestCode, final RemotingProcessor processor,
                           final ExecutorService executor);

    /**
     * 注册默认请求处理器
     */
    void registerDefaultProcessor(final RemotingProcessor processor, final ExecutorService executor);


    /**
     * 同步调用
     */
    RemotingCommand invokeSync(final Channel channel, final RemotingCommand request,
                               final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
            RemotingTimeoutException;

    /**
     * 异步调用
     */
    void invokeAsync(final Channel channel, final RemotingCommand request, final long timeoutMillis,
                     final AsyncCallback asyncCallback) throws InterruptedException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 单向调用
     */
    void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
            RemotingSendRequestException;


    void shutdown();
}
