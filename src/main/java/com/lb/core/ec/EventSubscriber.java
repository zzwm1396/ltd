package com.lb.core.ec;

import lombok.Getter;
import lombok.Setter;

/**
 * 事件订阅者
 * Created by libo on 2017/4/10.
 */
public class EventSubscriber {
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private Observer observer;
    public EventSubscriber(String id, Observer observer){
        this.id = id;
        this.observer = observer;
    }
}
