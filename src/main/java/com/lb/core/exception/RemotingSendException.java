package com.lb.core.exception;

/**
 * Created by libo on 2017/4/17.
 */
public class RemotingSendException extends Exception {

    private static final long serialVersionUID = -9213358329265153888L;

    public RemotingSendException() {
        super();
    }

    public RemotingSendException(String message) {
        super(message);
    }

    public RemotingSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemotingSendException(Throwable cause) {
        super(cause);
    }

}
