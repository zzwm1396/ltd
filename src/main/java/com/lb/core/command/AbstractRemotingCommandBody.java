package com.lb.core.command;

import com.lb.core.annotation.NotNull;
import com.lb.core.remoting.RemotingCommandBody;
import com.lb.core.remoting.exception.RemotingCommandFieldCheckException;
import com.lb.core.support.SystemClock;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libo on 2017/4/11.
 */
public class AbstractRemotingCommandBody implements RemotingCommandBody {

    private static final long serialVersionUID = 6674681619740624828L;

    /**
     * 节点组，当前节点的group
     */
    @NotNull
    @Setter
    @Getter
    private String nodeGroup;

    /**
     * 节点类型（NodeType的字符串表示）
     */
    @NotNull
    @Setter
    @Getter
    private String NodeType;

    /**
     * 当前节点的唯一标识
     */
    @NotNull
    @Setter
    @Getter
    private String identity;

    @Setter
    @Getter
    private Long timesTamp = SystemClock.now();

    @Setter
    @Getter
    private Map<String, Object> extParams;

    public void putExtParams(String key, Object object){
        if (this.extParams == null){
            this.extParams = new HashMap<String, Object>();
        }
        this.extParams.put(key, object);
    }

    @Override
    public void checkFileds() throws RemotingCommandFieldCheckException {

    }
}
