package com.lb.core.remoting.exception;

/**
 * RemotingCommandFieldCheckException
 * Created by libo on 2017/4/17.
 */
public class RemotingCommandFieldCheckException extends Exception {

    private static final long serialVersionUID = -5191974014987221682L;

    public RemotingCommandFieldCheckException(String message) {
        super(message);
    }

    public RemotingCommandFieldCheckException(String message, Throwable cause){
        super(message, cause);
    }
}
