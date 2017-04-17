package com.lb.core.monitor;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.constant.ExtConfig;
import com.lb.core.domain.monitor.MData;
import com.lb.core.factory.NamedThreadFactory;
import com.lb.core.monitor.jvmmonitor.JVMMonitor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象的监控中心
 * Created by libo on 2017/4/17.
 */
@Slf4j
public abstract class AbstractMonitorStatReporter implements MonitorStatReporter{
    protected AppContext appContext;
    protected Config config;

    private ScheduledExecutorService executor = Executors
            .newSingleThreadScheduledExecutor(new NamedThreadFactory("LTS-Monitor-data-collector", true));
    private ScheduledFuture<?> scheduledFuture;
    private AtomicBoolean start = new AtomicBoolean(false);

    public AbstractMonitorStatReporter(AppContext appContext) {
        this.appContext = appContext;
        this.config = appContext.getConfig();
    }

    public final void start() {

        // 启动JVM监控
        JVMMonitor.start();

        try {
            if (!config.getParameter(ExtConfig.M_STAT_REPORTER_CLOSED, false)) {
                if (start.compareAndSet(false, true)) {
                    scheduledFuture = executor.scheduleWithFixedDelay(
                            new MonitorStatReportWorker(appContext, this), 1, 1, TimeUnit.SECONDS);
                    log.info("MStatReporter start succeed.");
                }
            }
        } catch (Exception e) {
            log.error("MStatReporter start failed.", e);
        }
    }

    /**
     * 用来收集数据
     */
    protected abstract MData collectMData();

    protected abstract NodeType getNodeType();

    public final void stop() {
        try {
            if (start.compareAndSet(true, false)) {
                scheduledFuture.cancel(true);
                executor.shutdown();
                JVMMonitor.stop();
                log.info("MStatReporter stop succeed.");
            }
        } catch (Exception e) {
            log.error("MStatReporter stop failed.", e);
        }
    }
}
