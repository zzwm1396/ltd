package com.lb.core.remoting.exception;

/**
 * 远程命令参数检查异常
 * Created by libo on 2017/4/11.
 */
public class RemotingCommandFieldCheckException extends Exception {

    private static final long serialVersionUID = -5681230884225777824L;

    public RemotingCommandFieldCheckException(String message){
        super(message);
    }

    public RemotingCommandFieldCheckException(String message, Throwable cause){
        super(message, cause);
    }

    public RemotingCommandFieldCheckException(Throwable cause){
        super(cause);
    }
}
