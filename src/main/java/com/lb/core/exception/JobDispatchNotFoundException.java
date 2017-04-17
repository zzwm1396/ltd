package com.lb.core.exception;

/**
 * 任务调度器找不到异常
 * Created by libo on 2017/4/17.
 */
public class JobDispatchNotFoundException extends Exception {

    private static final long serialVersionUID = 5364147339391595615L;

    public JobDispatchNotFoundException() {

    }

    public JobDispatchNotFoundException(String message) {
        super(message);
    }

    public JobDispatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobDispatchNotFoundException(Throwable cause) {
        super(cause);
    }
}
