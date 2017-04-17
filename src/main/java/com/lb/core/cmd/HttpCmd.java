package com.lb.core.cmd;

import com.alibaba.fastjson.JSON;
import com.lb.core.commons.utils.StringUtils;
import com.lb.core.commons.utils.WebUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by libo on 2017/4/17.
 */
public class HttpCmd<Resp extends HttpCmdResponse> extends HttpCmdRequest {
    final public Resp doGet(String url) throws IOException {

        Resp resp = null;
        String result = null;
        try {
            result = WebUtils.doGet(url, null);
        } catch (IOException e1) {
            try {
                resp = (Resp) getResponseClass().newInstance();
                resp.setSuccess(false);
                resp.setMsg("GET ERROR: url=" + url + ", errorMsg=" + e1.getMessage());
                return resp;
            } catch (InstantiationException e) {
                throw new HttpCmdException(e);
            } catch (IllegalAccessException e) {
                throw new HttpCmdException(e);
            }
        }
        if (StringUtils.isNotEmpty(result)) {
            resp = (Resp) JSON.parseObject(result, getResponseClass());
        }
        return resp;
    }

    protected Class<? extends HttpCmdResponse> getResponseClass() {
        return HttpCmdResponse.class;
    }

    public Resp doPost(String url, Map<String, String> params) {
        Resp resp = null;
        String result = null;
        try {
            result = WebUtils.doPost(url, params, 3000, 30000);
        } catch (IOException e1) {
            try {
                resp = (Resp) getResponseClass().newInstance();
                resp.setSuccess(false);
                resp.setMsg("POST ERROR: url=" + url + ", errorMsg=" + e1.getMessage());
                return resp;
            } catch (InstantiationException e) {
                throw new HttpCmdException(e);
            } catch (IllegalAccessException e) {
                throw new HttpCmdException(e);
            }
        }
        if (StringUtils.isNotEmpty(result)) {
            resp = (Resp) JSON.parseObject(result, getResponseClass());
        }
        return resp;
    }
}
