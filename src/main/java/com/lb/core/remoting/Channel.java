package com.lb.core.remoting;



import java.net.SocketAddress;

/**
 * Created by libo on 2017/4/17.
 */
public interface Channel {
    SocketAddress localAddress();

    SocketAddress remoteAddress();

    ChannelHandler writeAndFlush(Object msg);

    ChannelHandler close();

    boolean isConnected();

    boolean isOpen();

    boolean isClosed();
}
