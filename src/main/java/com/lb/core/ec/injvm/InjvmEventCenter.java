package com.lb.core.ec.injvm;

import com.lb.core.commons.concurrent.ConcurrentHashSet;
import com.lb.core.constant.Constants;
import com.lb.core.ec.EventCenter;
import com.lb.core.ec.EventInfo;
import com.lb.core.ec.EventSubscriber;
import com.lb.core.factory.NameThreadFactory;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by libo on 2017/4/10.
 */
public class InjvmEventCenter implements EventCenter {

    private static final Logger logger = Logger.getLogger(InjvmEventCenter.class);

    private final ConcurrentHashMap<String, Set<EventSubscriber>> ecMap = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(Constants.AVAILABLE_PROCESSOR * 2,
            new NameThreadFactory("LTD_InjvmEventCenter-Executor", true));


    @Override
    public void subscribe(EventSubscriber subscriber, String... topics) {
        for (String topic : topics) {
            Set<EventSubscriber> subscribers = ecMap.get(topic);
            if (subscribers == null){
                subscribers = new ConcurrentHashSet<EventSubscriber>();
                Set<EventSubscriber> oldSubscribers = ecMap.putIfAbsent(topic, subscribers);
                if (oldSubscribers != null){
                    subscribers = oldSubscribers;
                }
            }
            subscribers.add(subscriber);
        }
    }

    @Override
    public void unSubscribe(EventSubscriber subscriber, String topic) {
        Set<EventSubscriber> subscribers = ecMap.get(topic);
        if (subscribers != null){
            for (EventSubscriber eventSubscriber : subscribers) {
                if (eventSubscriber.getId().equals(subscriber.getId())){
                    subscribers.remove(eventSubscriber);
                }
            }
        }
    }

    @Override
    public void publishSync(EventInfo eventInfo) {
        Set<EventSubscriber> subscribers = ecMap.get(eventInfo.getTopic());
        if (subscribers != null){
            for (EventSubscriber subscriber: subscribers) {
                eventInfo.setTopic(eventInfo.getTopic());
                subscriber.getObserver().onObserved(eventInfo);
            }
        }
    }

    @Override
    public void publishAsync(final EventInfo eventInfo) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String topic = eventInfo.getTopic();
                Set<EventSubscriber> subscribers = ecMap.get(topic);
                if (subscribers != null){
                    for (EventSubscriber subscriber : subscribers) {
                        eventInfo.setTopic(topic);
                        subscriber.getObserver().onObserved(eventInfo);
                    }
                }
            }
        });
    }
}
