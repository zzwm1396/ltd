package com.lb.core.command;

import com.lb.core.annotation.NotNull;
import com.lb.core.annotation.Nullable;
import com.lb.core.remoting.RemotingCommandBody;
import com.lb.core.remoting.exception.RemotingCommandFieldCheckException;
import com.lb.core.support.SystemClock;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象的远程命令body
 * Created by libo on 2017/4/14.
 */
public class AbstractRemotingCommandBody implements RemotingCommandBody {
    /**
     * 节点组 当前节点的 group(统一类型, 具有相同功能的节点group相同)
     */
    @NotNull
    @Setter
    @Getter
    private String nodeGroup;

    /**
     * NodeType 的字符串表示, 节点类型
     */
    @NotNull
    @Setter
    @Getter
    private String nodeType;

    /**
     * 当前节点的唯一标识
     */
    @NotNull
    @Setter
    @Getter
    private String identity;

    /**
     * 当前时间
     */
    @Setter
    @Getter
    private Long timestamp = SystemClock.now();

    /**
     * 额外的参数
     */
    @Nullable
    @Setter
    @Getter
    private Map<String, Object> extParams;

    public void putExtParam(String key, Object obj) {
        if (this.extParams == null) {
            this.extParams = new HashMap<String, Object>();
        }
        this.extParams.put(key, obj);
    }

    @Override
    public void checkFields() throws RemotingCommandFieldCheckException {

    }
}
