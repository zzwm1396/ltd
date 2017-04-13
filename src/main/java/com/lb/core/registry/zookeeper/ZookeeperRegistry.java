package com.lb.core.registry.zookeeper;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node;
import com.lb.core.registry.FailbackRegistry;
import com.lb.core.registry.NotifyListener;

/**
 * Created by libo on 2017/4/13.
 */
public class ZookeeperRegistry extends FailbackRegistry {
    public ZookeeperRegistry(final AppContext appContext) {
        super(appContext);

    }

    @Override
    protected void doRegister(Node node) {

    }

    @Override
    protected void doUnRegister(Node node) {

    }

    @Override
    protected void doSubscribe(Node node, NotifyListener listener) {

    }

    @Override
    protected void doUnsubscribe(Node node, NotifyListener listener) {

    }
}
