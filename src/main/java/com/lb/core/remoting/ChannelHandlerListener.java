package com.lb.core.remoting;

import java.util.EventListener;

/**
 * Created by libo on 2017/4/17.
 */
public interface ChannelHandlerListener extends EventListener{

    void operationComplete(Future future) throws Exception;
}
