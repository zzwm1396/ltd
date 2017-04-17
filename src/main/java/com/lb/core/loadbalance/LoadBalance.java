package com.lb.core.loadbalance;

import com.lb.core.annotation.SPI;
import com.lb.core.constant.ExtConfig;

import java.util.List;

/**
 * Created by libo on 2017/4/17.
 */
@SPI(key = ExtConfig.LOADBALANCE, dftValue = "random")
public interface LoadBalance {
    public <S> S select(List<S> shards, String seed);
}
