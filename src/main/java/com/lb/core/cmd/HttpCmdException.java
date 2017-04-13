package com.lb.core.cmd;

/**
 * Created by libo on 2017/4/13.
 */
public class HttpCmdException extends RuntimeException{
    private static final long serialVersionUID = 7563802613921477340L;

    public HttpCmdException() {
        super();
    }

    public HttpCmdException(String message) {
        super(message);
    }

    public HttpCmdException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpCmdException(Throwable cause) {
        super(cause);
    }
}
