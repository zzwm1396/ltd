package com.lb.core.loadbanlance;

import com.lb.core.constant.ExtConfig;
import com.lb.core.spi.SPI;

import java.util.List;

/**
 * Created by libo on 2017/4/13.
 */
@SPI(key = ExtConfig.LOADBALANCE, dftValue = "random")
public interface LoadBalance {
    public <S> S select(List<S> shards, String seed);
}
