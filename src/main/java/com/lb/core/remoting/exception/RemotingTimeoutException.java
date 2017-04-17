package com.lb.core.remoting.exception;

/**
 * RPC调用超时异常
 * Created by libo on 2017/4/17.
 */
public class RemotingTimeoutException extends RemotingException {

    private static final long serialVersionUID = -3479655773575269398L;

    public RemotingTimeoutException(String message) {
        super(message);
    }

    public RemotingTimeoutException(String addr, long timeoutMillis) {
        this(addr, timeoutMillis, null);
    }

    public RemotingTimeoutException(String addr, long timeoutMillis, Throwable cause) {
        super("wait response on the channel <" + addr + "> timeout, " + timeoutMillis + "(ms)", cause);
    }
}
