package com.lb.core.remoting;

import com.lb.core.AppContext;
import com.lb.core.exception.RemotingSendException;
import com.lb.core.remoting.exception.RemotingException;
import org.apache.zookeeper.AsyncCallback;

import java.util.concurrent.ExecutorService;

/**
 * RemotingServer包装类
 * Created by libo on 2017/4/17.
 */
public class RemotingServerDelegate {

    private RemotingServer remotingServer;
    private AppContext appContext;

    public RemotingServerDelegate(RemotingServer remotingServer, AppContext appContext) {
        this.remotingServer = remotingServer;
        this.appContext = appContext;
    }

    public void start() {
        try {
            remotingServer.start();
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerProcessor(int requestCode, RemotingProcessor processor,
                                  ExecutorService executor) {
        remotingServer.registerProcessor(requestCode, processor, executor);
    }

    public void registerDefaultProcessor(RemotingProcessor processor, ExecutorService executor) {
        remotingServer.registerDefaultProcessor(processor, executor);
    }

    public RemotingCommand invokeSync(Channel channel, RemotingCommand request)
            throws RemotingSendException {
        try {

            return remotingServer.invokeSync(channel, request,
                    appContext.getConfig().getInvokeTimeOutMillis());
        } catch (Throwable t) {
            throw new RemotingSendException(t);
        }
    }

    public void invokeAsync(Channel channel, RemotingCommand request, AsyncCallback asyncCallback)
            throws RemotingSendException {
        try {

            remotingServer.invokeAsync(channel, request,
                    appContext.getConfig().getInvokeTimeOutMillis(), asyncCallback);
        } catch (Throwable t) {
            throw new RemotingSendException(t);
        }
    }

    public void invokeOneway(Channel channel, RemotingCommand request)
            throws RemotingSendException {
        try {

            remotingServer.invokeOneway(channel, request,
                    appContext.getConfig().getInvokeTimeOutMillis());
        } catch (Throwable t) {
            throw new RemotingSendException(t);
        }
    }

    public void shutdown() {
        remotingServer.shutdown();
    }
}
