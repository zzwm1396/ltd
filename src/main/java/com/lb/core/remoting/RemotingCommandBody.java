package com.lb.core.remoting;

import com.lb.core.remoting.exception.RemotingCommandFieldCheckException;

/**
 * 字段检查
 * Created by libo on 2017/4/17.
 */
public interface RemotingCommandBody {
    public void checkFields() throws RemotingCommandFieldCheckException;
}
