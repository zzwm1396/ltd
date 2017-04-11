package com.lb.core.remoting;

import com.lb.core.remoting.exception.RemotingCommandFieldCheckException;

import java.io.Serializable;

/**
 * RemotingCommand中自定义字段反射对象的公共接口
 * Created by libo on 2017/4/11.
 */
public interface RemotingCommandBody extends Serializable {
    public void checkFileds() throws RemotingCommandFieldCheckException;
}
