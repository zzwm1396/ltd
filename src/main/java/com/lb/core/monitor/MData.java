package com.lb.core.monitor;

/**
 * Created by libo on 2017/4/12.
 */
public class MData {
    private Long timestamp;

    private JvmMData jvmMData;

    public JvmMData getJvmMData() {
        return jvmMData;
    }

    public void setJvmMData(JvmMData jvmMData) {
        this.jvmMData = jvmMData;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
