package com.lb.core.remoting.serialize;

import com.lb.core.cluster.constant.Constants;
import com.lb.core.spi.ServiceLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 序列化适配器
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class AdaptiveSerializable implements RemotingSerializable {
    
    private static volatile String defaultSerializable;

    private static final Map<Integer, RemotingSerializable>
            ID_SERIALIZABLE_MAP = new HashMap<Integer, RemotingSerializable>();

    static {
        Set<String> names = ServiceLoader.getServiceProviders(RemotingSerializable.class);
        for (String name : names) {
            if (!Constants.ADAPTIVE.equalsIgnoreCase(name)) {
                RemotingSerializable serializable = ServiceLoader.load(RemotingSerializable.class, name);
                ID_SERIALIZABLE_MAP.put(serializable.getId(), serializable);
            }
        }
    }

    public static RemotingSerializable getSerializableById(int id) {
        return ID_SERIALIZABLE_MAP.get(id);
    }

    public static void setDefaultSerializable(String defaultSerializable) {
        AdaptiveSerializable.defaultSerializable = defaultSerializable;
        log.info("Using defaultSerializable [{}]", defaultSerializable);
    }

    private RemotingSerializable getRemotingSerializable() {
        RemotingSerializable remotingSerializable;

        String serializable = defaultSerializable; // copy reference
        if (serializable != null) {
            remotingSerializable = ServiceLoader.load(RemotingSerializable.class, serializable);
        } else {
            remotingSerializable = ServiceLoader.loadDefault(RemotingSerializable.class);
        }
        return remotingSerializable;
    }



    @Override
    public int getId() {
        return getRemotingSerializable().getId();
    }

    @Override
    public byte[] serialize(Object obj) throws Exception {
        return getRemotingSerializable().serialize(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        return getRemotingSerializable().deserialize(data, clazz);
    }
}
