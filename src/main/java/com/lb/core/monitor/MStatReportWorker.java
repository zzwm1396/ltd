package com.lb.core.monitor;

import com.lb.core.AppContext;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by libo on 2017/4/12.
 */
public class MStatReportWorker implements Runnable {

    protected final Logger LOGGER = LoggerFactory.getLogger(MStatReportWorker.class);

    // 间隔一分钟
    private int interval = 1;

    // 上一分钟
    private Integer preMinute = null;

    // 配置信息
    private AppContext appContext;

    // 内存监视信息
    private AbstractMStatReporter reporter;

    private PriorityBlockingQueue

    @Override
    public void run() {

    }
}
