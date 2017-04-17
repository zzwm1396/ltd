package com.lb.core.remoting.exception;

/**
 * client链接Server异常
 * Created by libo on 2017/4/17.
 */
public class RemotingConnectException extends RemotingException {

    private static final long serialVersionUID = -326493284525649906L;

    public RemotingConnectException(String addr) {
        this(addr, null);
    }

    public RemotingConnectException(String addr, Throwable cause) {
        super("connect to <" + addr + "> failed", cause);
    }
}
