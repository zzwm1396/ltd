package com.lb.core.registry;

import com.lb.core.AppContext;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.ec.EventInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 注册中心状态监控
 * Created by libo on 2017/4/15.
 */
@Slf4j
public class RegistryStatMonitor {
    private AppContext appContext;
    private AtomicBoolean available = new AtomicBoolean(false);

    public RegistryStatMonitor(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * 设置节点状态
     * @param available 节点状态
     */
    public void setAvailable(boolean available) {
        this.available.set(available);

        if (log.isInfoEnabled()) {
            log.info("Registry {}", available ? "available" : "unavailable");
        }
        // 发布事件
        appContext.getEventCenter().publishAsync(new EventInfo(
                available ? EcTopic.REGISTRY_AVAILABLE : EcTopic.REGISTRY_UN_AVAILABLE));
    }

    /**
     * 节点是否可用
     * @return 是否可用
     */
    public boolean isAvailable() {
        return this.available.get();
    }
}
