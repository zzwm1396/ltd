package com.lb.core.command;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;

/**
 * 用于设置Commandody 的基础信息
 * Created by libo on 2017/4/11.
 */
public class CommandBodyWrapper {

    private Config config;

    public CommandBodyWrapper(Config config){
        this.config = config;
    }

    public <T extends AbstractRemotingCommandBody> T wrapper(T commandBody){
        commandBody.setNodeGroup(config.getNodeGroup());
        commandBody.setNodeType(config.getNodeType().name());
        commandBody.setIdentity(config.getIdentity());
        return commandBody;
    }

    public static <T extends AbstractRemotingCommandBody> T wrapper(AppContext appContext, T commandBody){
        return appContext.getCommandBodyWrapper().wrapper(commandBody);
    }
}
