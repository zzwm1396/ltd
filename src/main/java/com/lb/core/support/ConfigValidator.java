package com.lb.core.support;

/**
 * Created by libo on 2017/4/13.
 */
public class ConfigValidator {
    /**
     * 校验集群名称
     *
     * @param nodeGroup 集群名称
     */
    public static void validateNodeGroup(String nodeGroup) {

        if (nodeGroup == null || nodeGroup.trim().equals(""))
            throw new IllegalArgumentException("nodeGroup should not be null");
        if (nodeGroup.length() > 64) {
            throw new IllegalArgumentException("nodeGroup length should not great than 64");
        }
    }

    /**
     * 校验clusterName
     * @param clusterName clusterName
     */
    public static void validateClusterName(String clusterName) {

        if (clusterName == null || clusterName.trim().equals("")) {
            throw new IllegalArgumentException("clusterName should not be null");
        }
        if (clusterName.length() > 64) {
            throw new IllegalArgumentException("clusterName length should not great than 64");
        }

    }

    /**
     * 校验identity
     * @param identity 唯一标识
     */
    public static void validateIdentity(String identity) {
        if (identity != null || !"".equals(identity.trim())) {
            if (identity.length() > 64)
                throw new IllegalArgumentException("identity length should not great than 64");
        }
    }
}
