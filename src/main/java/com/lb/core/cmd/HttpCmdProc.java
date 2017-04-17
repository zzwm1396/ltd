package com.lb.core.cmd;

/**
 * Cmd 处理器
 * Created by libo on 2017/4/17.
 */
public interface HttpCmdProc {

    /**
     * 获取节点标识
     * @return 节点标识
     */
    String getNodeIdentity();

    /**
     * 获取command信息
     * @return command信息
     */
    String getCommand();

    /**
     * 执行命令
     * @param request 请求
     * @return httpCmd响应
     * @throws Exception 执行异常
     */
    HttpCmdResponse execute(HttpCmdRequest request) throws Exception;
}
