package com.lb.core.cmd;

/**
 * Created by libo on 2017/4/13.
 */
public interface HttpCmdProc {
    String nodeIdentity();

    String getCommand();

    HttpCmdResponse execute(HttpCmdRequest request) throws Exception;
}
