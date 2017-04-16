package com.lb.core.registry.zookeeper;

import com.lb.core.annotation.SPI;
import com.lb.core.cluster.Config;
import com.lb.core.constant.ExtConfig;

/**
 * zookeeper包装器
 * Created by libo on 2017/4/15.
 */
@SPI(key = ExtConfig.ZK_CLIENT_KEY, dftValue = "zkClient")
public interface ZookeeperTransporter {
    ZkClient connect(Config config);
}
