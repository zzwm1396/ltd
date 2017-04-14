package com.lb.core.command;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;

/**
 * CommandBody 包装器，
 * 用于设置一些基本嘻嘻
 * Created by libo on 2017/4/14.
 */
public class CommandBodyWrapper {
    private Config config;

    public CommandBodyWrapper(Config config) {
        this.config = config;
    }

    public <T extends AbstractRemotingCommandBody> T wrapper(T commandBody) {
        commandBody.setNodeGroup(config.getNodeGroup());
        commandBody.setNodeType(config.getNodeType().name());
        commandBody.setIdentity(config.getIdentity());
        return commandBody;
    }

    public static <T extends AbstractRemotingCommandBody> T wrapper(AppContext appContext, T commandBody) {
        return appContext.getCommandBodyWrapper().wrapper(commandBody);
    }
}
