package com.lb.core.cmd;

import com.lb.core.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpCmd请求
 * Created by libo on 2017/4/17.
 */
@Getter
@Setter
public class HttpCmdRequest {
    private String command;
    private String nodeIdentity;

    private Map<String, String> params;



    public String getParam(String key) {
        if (params != null) {
            return params.get(key);
        }
        return null;
    }

    public String getParam(String key, String defaultValue) {
        if (params != null) {
            String value = params.get(key);
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return value;
        }
        return null;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put(key, value);
    }
}
