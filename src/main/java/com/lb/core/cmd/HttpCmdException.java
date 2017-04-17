package com.lb.core.cmd;

/**
 * httpCmd 异常
 * Created by libo on 2017/4/17.
 */
public class HttpCmdException extends RuntimeException {

    private static final long serialVersionUID = 4291715675271829928L;

    public HttpCmdException(){
        super();
    }

    public HttpCmdException(String message){
        super(message);
    }

    public HttpCmdException(Throwable throwable){
        super(throwable);
    }

    public HttpCmdException(String message, Throwable throwable){
        super(message, throwable);
    }
}
