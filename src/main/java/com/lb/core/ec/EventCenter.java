package com.lb.core.ec;

/**
 * 事件中心接口
 * Created by libo on 2017/4/14.
 */
public interface EventCenter {
    /**
     * 订阅主题
     */
    public void subscribe(EventSubscriber subscriber, String... topics);

    /**
     * 取消订阅主题
     */
    public void unSubscribe(String topic, EventSubscriber subscriber);

    /**
     * 同步发布主题消息
     */
    public void publishSync(EventInfo eventInfo);

    /**
     * 异步发送主题消息
     */
    public void publishAsync(EventInfo eventInfo);

}
