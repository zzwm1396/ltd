package com.lb.core;

import com.lb.core.cluster.Config;
import com.lb.core.ec.EventCenter;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by libo on 2017/4/7.
 */
public abstract class AppContext {
    @Getter
    @Setter
    private EventCenter eventCenter;

    @Setter
    @Getter
    private Config  config;
}
