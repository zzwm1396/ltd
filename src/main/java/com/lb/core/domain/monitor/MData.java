package com.lb.core.domain.monitor;

import lombok.Getter;
import lombok.Setter;

/**
 * MData
 * Created by libo on 2017/4/17.
 */
@Getter
@Setter
public class MData {
    private Long timestamp;

    private JvmMData jvmMData;
}
