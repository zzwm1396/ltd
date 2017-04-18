package com.lb.core.remoting;

import com.lb.core.remoting.exception.RemotingCommandFieldCheckException;

import java.io.Serializable;

/**
 * 字段检查
 * Created by libo on 2017/4/17.
 */
public interface RemotingCommandBody  extends Serializable{
    public void checkFields() throws RemotingCommandFieldCheckException;
}
