package com.lb.core.remoting;

import com.lb.core.remoting.exception.RemotingCommandException;

/**
 * 接受请求处理器， 服务器与客户端通用
 * Created by libo on 2017/4/17.
 */
public interface RemotingProcessor {

    public RemotingCommand processRequest(Channel channel, RemotingCommand request)
            throws RemotingCommandException;
}
