package com.lb.core.registry;

import com.lb.core.AppContext;
import com.lb.core.constant.EcTopic;
import com.lb.core.ec.EventInfo;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by libo on 2017/4/13.
 */
public class RegistryStatMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryStatMonitor.class);
    private AppContext appContext;
    private AtomicBoolean available = new AtomicBoolean(false);

    public RegistryStatMonitor(AppContext appContext) {
        this.appContext = appContext;
    }

    public void setAvailable(boolean available) {
        this.available.set(available);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Registry {}", available ? "available" : "unavailable");
        }
        // 发布事件
        appContext.getEventCenter().publishAsync(new EventInfo(
                available ? EcTopic.REGISTRY_AVAILABLE : EcTopic.REGISTRY_UN_AVAILABLE));
    }

    public boolean isAvailable() {
        return this.available.get();
    }

}
