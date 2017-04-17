package com.lb.core.remoting.exception;

/**
 * 远程通信异常
 * Created by libo on 2017/4/17.
 */
public class RemotingException extends Exception{
    private static final long serialVersionUID = -5690687334570505110L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
