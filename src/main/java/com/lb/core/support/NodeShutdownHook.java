package com.lb.core.support;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.Callable;
import com.lb.core.constant.EcTopic;
import com.lb.core.ec.EventInfo;
import com.lb.core.ec.EventSubscriber;
import com.lb.core.ec.Observer;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;



/**
 * Created by libo on 2017/4/13.
 */
public class NodeShutdownHook {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeShutdownHook.class);

    public static void registerHook(AppContext appContext, final String name, final Callable callback) {
        appContext.getEventCenter().subscribe(new EventSubscriber(name + "_" + appContext.getConfig().getIdentity(), new Observer() {
            @Override
            public void onObserved(EventInfo eventInfo) {
                if (callback != null) {
                    try {
                        callback.call();
                    } catch (Exception e) {
                        LOGGER.warn("Call shutdown hook {} error", name, e);
                    }
                }
            }
        }), EcTopic.NODE_SHUT_DOWN);
    }
}
