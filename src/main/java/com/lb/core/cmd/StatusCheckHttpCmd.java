package com.lb.core.cmd;

import com.lb.core.cluster.Config;

/**
 * 状态检查
 * Created by libo on 2017/4/17.
 */
public class StatusCheckHttpCmd implements HttpCmdProc {

    private Config config;

    public StatusCheckHttpCmd(Config config) {
        this.config = config;
    }

    @Override
    public String getNodeIdentity() {
        return config.getIdentity();
    }

    @Override
    public String getCommand() {
        return HttpCmdNames.HTTP_CMD_STATUS_CHECK;
    }

    @Override
    public HttpCmdResponse execute(HttpCmdRequest request) throws Exception {
        HttpCmdResponse response = new HttpCmdResponse();
        response.setSuccess(true);
        response.setMsg("ok");
        return response;
    }
}
