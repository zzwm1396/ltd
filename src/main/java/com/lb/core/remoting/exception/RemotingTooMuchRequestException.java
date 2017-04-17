package com.lb.core.remoting.exception;

/**
 * 异步调用或者oneway调用，堆积请求超过信号最大值异常
 * Created by libo on 2017/4/17.
 */
public class RemotingTooMuchRequestException extends RemotingException {

    private static final long serialVersionUID = -2691558991248740000L;

    public RemotingTooMuchRequestException(String message) {
        super(message);
    }
}
