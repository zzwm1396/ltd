package com.lb.core.factory;

import lombok.Getter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by libo on 2017/4/10.
 */
public class NameThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger threadNum = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemon;

    @Getter
    private final ThreadGroup group;

    public NameThreadFactory(String prefix, boolean daemon){
        this.prefix = prefix;
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        group = s == null ? Thread.currentThread().getThreadGroup(): s.getThreadGroup();
    }

    public NameThreadFactory(String prefix){
        this(prefix, false);

    }

    public NameThreadFactory(){
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + threadNum.getAndIncrement();
        Thread ret = new Thread(group, r, name, 0);
        ret.setDaemon(daemon);
        return ret;
    }
}
