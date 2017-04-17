package com.lb.core.factory;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.StringUtils;
import com.lb.core.registry.base.Registry;
import com.lb.core.registry.zookeeper.ZookeeperRegistry;

/**
 * 注册中心工厂类
 * Created by libo on 2017/4/17.
 */
public class RegistryFactory {

    public static Registry getRegistry(AppContext appContext) {

        String address = appContext.getConfig().getRegistryAddress();
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("address is null！");
        }
        if (address.startsWith("zookeeper://")) {
            return new ZookeeperRegistry(appContext);
        }
        throw new IllegalArgumentException("illegal address protocol");
    }
}
