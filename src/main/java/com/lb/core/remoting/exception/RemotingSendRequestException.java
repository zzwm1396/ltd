package com.lb.core.remoting.exception;

/**
 * RPC调用中，客户端发送请求失败异常
 * Created by libo on 2017/4/17.
 */
public class RemotingSendRequestException extends RemotingException {

    private static final long serialVersionUID = -109747309366620383L;

    public RemotingSendRequestException(String addr) {
        this(addr, null);
    }

    public RemotingSendRequestException(String addr, Throwable cause) {
        super("send request to <" + addr + "> failed", cause);
    }
}
