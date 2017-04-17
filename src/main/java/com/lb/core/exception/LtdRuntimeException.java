package com.lb.core.exception;

/**
 * Ltd运行时异常
 * Created by libo on 2017/4/17.
 */
public class LtdRuntimeException extends RuntimeException {

    public LtdRuntimeException() {
        super();
    }

    public LtdRuntimeException(String message) {
        super(message);
    }

    public LtdRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LtdRuntimeException(Throwable cause) {
        super(cause);
    }
}

