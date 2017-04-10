package com.lb.core.ec;

/**
 * 事件观察者接口
 * Created by libo on 2017/4/10.
 */
public interface Observer {
    public void onObserved(EventInfo eventInfo);
}
