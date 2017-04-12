package com.lb.core.monitor;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;
import com.lb.core.constant.ExtConfig;
import com.lb.core.factory.NameThreadFactory;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;
import com.lb.core.monitor.jvmmonitor.JVMMonitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by libo on 2017/4/11.
 */
public abstract class AbstractMStatReporter implements MStatReporter {

    protected  final Logger LOGGER = LoggerFactory.getLogger(AbstractMStatReporter.class);

    protected AppContext appContext;
    protected Config config;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new NameThreadFactory(
            "LTS-monitor-data-collector", true));

    private ScheduledFuture<?> scheduledFuture;

    private AtomicBoolean start = new AtomicBoolean(false);

    public AbstractMStatReporter(AppContext appContext){
        this.appContext = appContext;
        this.config = appContext.getConfig();
    }

    @Override
    public void start() {
        JVMMonitor.start();

        try{
            if (!config.getParameter(ExtConfig.M_STAT_REPORTER_CLOSED, false)){
                if (start.compareAndSet(false, true)){
                    scheduledFuture = executor.scheduleWithFixedDelay(
                        new MStatReporterWork(appContext, this), 1, 1, TimeUnit.SECONDS
                    );
                }
            }
        }

    }

    @Override
    public void stop() {

    }
}
