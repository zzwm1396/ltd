package com.lb.core.ec;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件信息
 * Created by libo on 2017/4/10.
 */
public class EventInfo {

    @Getter
    @Setter
    private String topic;

    private Map<String, Object> params;

    public EventInfo(String topic){
        this.topic = topic;
    }

    public void setParam(String key, Object value){
        if (params == null){
            params = new HashMap<String, Object>();
        }
        params.put(key, value);
    }

    public Object removeParam(String key){
        if (params != null){
            return params.remove(key);
        }
        return null;
    }

    public Object getParam(String key){
        if (params != null){
            return params.get(key);
        }
        return null;
    }

    public Map<String, Object> getParams(){
        return params == null ? new HashMap<String, Object>() : params;
    }
}
