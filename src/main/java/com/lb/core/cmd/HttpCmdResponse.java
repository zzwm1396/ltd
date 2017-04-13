package com.lb.core.cmd;

import java.io.Serializable;

/**
 * Created by libo on 2017/4/13.
 */
public class HttpCmdResponse implements Serializable {
    private boolean success = false;
    private String msg;
    private String code;
    private String obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public static HttpCmdResponse newResponse(boolean success, String msg) {
        HttpCmdResponse response = new HttpCmdResponse();
        response.setSuccess(success);
        response.setMsg(msg);
        return response;
    }
}
