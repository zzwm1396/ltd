package com.lb.core.support;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 保持节点存活
 *
 * 注:  非守护线程
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class AliveKeeping {
    private static Timer timer;

    private static AtomicBoolean start = new AtomicBoolean(false);

    public static void start() {
        if (start.compareAndSet(false, true)) {
            timer = new Timer("AliveKeepingService");
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (log.isDebugEnabled()) {
                        log.debug("I'm alive");
                    }
                }
            }, 1000 * 60 * 10, 1000 * 60 * 10);
        }
    }

    public static void stop() {
        if (start.compareAndSet(true, false)) {
            if (timer != null) {
                timer.cancel();
            }
        }
    }
}
