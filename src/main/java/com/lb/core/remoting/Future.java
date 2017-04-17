package com.lb.core.remoting;

/**
 * Created by libo on 2017/4/17.
 */
public interface Future {

    boolean isSuccess();

    Throwable cause();
}
