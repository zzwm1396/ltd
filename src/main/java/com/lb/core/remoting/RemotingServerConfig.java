package com.lb.core.remoting;

import com.lb.core.cluster.constant.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务端配置
 * Created by libo on 2017/4/18.
 */
@Setter
@Getter
public class RemotingServerConfig {
    private int listenPort = 8888;
    private int serverWorkerThreads = 32;
    private int serverCallbackExecutorThreads = Constants.AVAILABLE_PROCESSOR * 2;
    private int serverSelectorThreads = Constants.AVAILABLE_PROCESSOR * 2;
    private int serverOnewaySemaphoreValue = 32;
    private int serverAsyncSemaphoreValue = 64;

    private int readerIdleTimeSeconds = 0;
    private int writerIdleTimeSeconds = 0;
    private int serverChannelMaxIdleTimeSeconds = 120;
}
