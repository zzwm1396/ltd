package com.lb.core.cmd;

import lombok.Getter;
import lombok.Setter;

/**
 * HttpCmd 响应
 * Created by libo on 2017/4/17.
 */
@Setter
@Getter
public class HttpCmdResponse {

    private boolean success = false;
    private String msg;
    private String code;
    private String obj;

    public boolean isSuccess() {
        return success;
    }

    public static HttpCmdResponse newResponse(boolean success, String msg) {
        HttpCmdResponse response = new HttpCmdResponse();
        response.setSuccess(success);
        response.setMsg(msg);
        return response;
    }
}
