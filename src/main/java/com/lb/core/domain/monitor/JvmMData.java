package com.lb.core.domain.monitor;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * JvmMData
 * Created by libo on 2017/4/17.
 */
@Setter
@Getter
public class JvmMData {
    private Map<String, Object> memoryMap;

    private Map<String, Object> gcMap;

    private Map<String, Object> threadMap;
}
