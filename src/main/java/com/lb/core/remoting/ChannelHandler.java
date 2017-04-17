package com.lb.core.remoting;

/**
 * 添加监听
 * Created by libo on 2017/4/17.
 */
public interface ChannelHandler {

    ChannelHandler addListener(ChannelHandlerListener listener);
}
