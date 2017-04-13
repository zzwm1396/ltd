package com.lb.core.registry;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.StringUtils;
import com.lb.core.registry.zookeeper.ZookeeperRegistry;

/**
 * Created by libo on 2017/4/13.
 */
public class RegistryFactory {
    public static Registry getRegistry(AppContext appContext){
        String address = appContext.getConfig().getRegistryAddress();
        if (StringUtils.isEmpty(address)){
            throw new IllegalArgumentException("address is null !");
        }
        if (address.startsWith("zookeeper://")){
            return new ZookeeperRegistry(appContext);
        }
        throw new IllegalArgumentException("illegal address protocol");
    }
}
