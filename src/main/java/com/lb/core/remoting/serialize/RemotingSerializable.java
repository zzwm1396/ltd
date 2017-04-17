package com.lb.core.remoting.serialize;

import com.lb.core.annotation.SPI;
import com.lb.core.constant.ExtConfig;

/**
 * Created by libo on 2017/4/17.
 */
@SPI(key = ExtConfig.REMOTING_SERIALIZABLE_DFT, dftValue = "fastjson")
public interface RemotingSerializable {
    int getId();

    byte[] serialize(final Object obj) throws Exception;

    <T> T deserialize(final byte[] data, Class<T> clazz) throws Exception;
}
