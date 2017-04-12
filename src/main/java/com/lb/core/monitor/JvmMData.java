package com.lb.core.monitor;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by libo on 2017/4/12.
 */
public class JvmMData {

    @Getter
    @Setter
    private Map<String, Object> memoryMap;

    @Getter
    @Setter
    private Map<String, Object> gcMap;

    @Getter
    @Setter
    private Map<String, Object> threadMap;
}
