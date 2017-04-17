package com.lb.core.cmd;

import com.alibaba.fastjson.JSON;
import com.lb.core.cluster.Config;
import com.lb.core.monitor.jvmmonitor.JVMCollector;

import java.util.Map;

/**
 * 用于获取jvm信息
 * Created by libo on 2017/4/17.
 */
public class JVMInfoGetHttpCmd implements HttpCmdProc{

    private Config config;

    public JVMInfoGetHttpCmd(Config config) {
        this.config = config;
    }

    @Override
    public String getNodeIdentity() {
        return config.getIdentity();
    }

    @Override
    public String getCommand() {
        return HttpCmdNames.HTTP_CMD_JVM_INFO_GET;
    }

    @Override
    public HttpCmdResponse execute(HttpCmdRequest request) throws Exception {

        Map<String, Object> jvmInfo = JVMCollector.getJVMInfo();

        HttpCmdResponse response = new HttpCmdResponse();
        response.setSuccess(true);
        response.setObj(JSON.toJSONString(jvmInfo));

        return response;
    }

}
