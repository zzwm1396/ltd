package com.lb.core.support;

import com.lb.core.commons.utils.StringUtils;

/**
 * config参数校验类
 * Created by libo on 2017/4/17.
 */
public class ConfigValidator {
    public static void validateNodeGroup(String nodeGroup) {
        if (StringUtils.isEmpty(nodeGroup)) {
            throw new IllegalArgumentException("nodeGroup should not be null");
        }
        if (nodeGroup.length() > 64) {
            throw new IllegalArgumentException("nodeGroup length should not great than 64");
        }
    }

    public static void validateClusterName(String clusterName) {
        if (StringUtils.isEmpty(clusterName)) {
            throw new IllegalArgumentException("clusterName should not be null");
        }
        if (clusterName.length() > 64) {
            throw new IllegalArgumentException("clusterName length should not great than 64");
        }
    }

    public static void validateIdentity(String identity) {
        if (StringUtils.isNotEmpty(identity)) {
            if (identity.length() > 64) {
                throw new IllegalArgumentException("identity length should not great than 64");
            }
        }
    }
}
