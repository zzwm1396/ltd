package com.lb.core.cluster.Node;

import com.lb.core.exception.ConfigPropertiesIllegalException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * conf参数信息类
 * Created by libo on 2017/4/17.
 */
@Setter
@Getter
public abstract class AbstractConfigProperties {
    /**
     * 节点标识(可选)
     */
    private String identity;
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * zookeeper地址
     */
    private String registryAddress;
    /**
     * 执行绑定的本地ip
     */
    private String bindIp;
    /**
     * 额外参数配置
     */
    private Map<String, String> configs = new HashMap<String, String>();

    /**
     * 检查参数
     * @throws ConfigPropertiesIllegalException config参数异常
     */
    public abstract void checkProperties() throws ConfigPropertiesIllegalException;

}
