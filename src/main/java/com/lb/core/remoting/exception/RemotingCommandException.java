package com.lb.core.remoting.exception;

/**
 * 命令解析自定义字段时，校验字段有效性抛出异常
 * Created by libo on 2017/4/17.
 */
public class RemotingCommandException  extends RemotingException{
    private static final long serialVersionUID = -2604113900945642891L;

    public RemotingCommandException(String message) {
        super(message, null);
    }

    public RemotingCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
