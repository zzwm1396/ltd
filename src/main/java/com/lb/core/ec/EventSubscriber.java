package com.lb.core.ec;

import lombok.Getter;
import lombok.Setter;

/**
 * 事件订阅者
 * Created by libo on 2017/4/14.
 */
public class EventSubscriber {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Observer observer;

    public EventSubscriber(String id, Observer observer) {
        this.id = id;
        this.observer = observer;
    }
}
