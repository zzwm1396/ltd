package com.lb.core.ec;

/**
 * 事件中心接口
 * Created by libo on 2017/4/10.
 */
public interface EventCenter {

    /**
     * 订阅主题
     */
    public void subscribe(EventSubscriber subscriber, String... topics);

    /**
     * 取消订阅主题
     */
    public void unSubscribe(EventSubscriber subscriber, String topic);

    /**
     * 同步发布主题
     */
    public void publishSync(EventInfo eventInfo);

    /**
     * 异步发布主题
     */
    public void publishAsync(EventInfo eventInfo);
}
