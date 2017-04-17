package com.lb.core.remoting;

import com.lb.core.remoting.exception.*;
import org.apache.zookeeper.AsyncCallback;

import java.util.concurrent.ExecutorService;

/**
 * 远程通信  client接口
 * Created by libo on 2017/4/17.
 */
public interface RemotingClient {
    void start() throws RemotingException;

    /**
     * 同步调用
     */
    RemotingCommand invokeSync(final String addr, final RemotingCommand request,
                               final long timeoutMillis) throws InterruptedException, RemotingConnectException,
            RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 异步调用
     */
    void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis,
                     final AsyncCallback asyncCallback) throws InterruptedException, RemotingConnectException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 单向调用
     */
    void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
            RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 注册处理器
     */
    void registerProcessor(final int requestCode, final RemotingProcessor processor,
                           final ExecutorService executor);

    /**
     * 注册默认处理器
     */
    void registerDefaultProcessor(final RemotingProcessor processor, final ExecutorService executor);

    void shutdown();
}
