package com.lb.core.registry;

import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.Node.NodeType;
import com.lb.core.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 节点注册辅助类
 * Created by libo on 2017/4/15.
 */
@Slf4j
public class NodeRegistryUtils {
    

    public static String getRootPath(String clusterName) {
        return "/LTS/" + clusterName + "/NODES";
    }

    public static String getNodeTypePath(String clusterName, NodeType nodeType) {
        return NodeRegistryUtils.getRootPath(clusterName) + "/" + nodeType;
    }

    public static Node parse(String fullPath) {
        try {
            Node node = new Node();
            String[] nodeDir = fullPath.split("/");
            NodeType nodeType = NodeType.valueOf(nodeDir[4]);
            node.setNodeType(nodeType);
            String url = nodeDir[5];

            url = url.substring(nodeType.name().length() + 3);
            String address = url.split("\\?")[0];
            String ip = address.split(":")[0];

            node.setIp(ip);
            if (address.contains(":")) {
                String port = address.split(":")[1];
                if (port != null && !"".equals(port.trim())) {
                    node.setPort(Integer.valueOf(port));
                }
            }
            String params = url.split("\\?")[1];

            String[] paramArr = params.split("&");
            for (String paramEntry : paramArr) {
                if (StringUtils.isEmpty(paramEntry)) {
                    continue;
                }
                String key = paramEntry.split("=")[0];
                String value = paramEntry.split("=")[1];
                if ("clusterName".equals(key)) {
                    node.setClusterName(value);
                } else if ("nodeGroup".equals(key)) {
                    node.setNodeGroup(value);
                } else if ("threads".equals(key)) {
                    node.setThreads(Integer.valueOf(value));
                } else if ("identity".equals(key)) {
                    node.setIdentity(value);
                } else if ("createTime".equals(key)) {
                    node.setCreateTime(Long.valueOf(value));
                } else if ("isAvailable".equals(key)) {
                    node.setAvailable(Boolean.valueOf(value));
                } else if ("hostName".equals(key)) {
                    node.setHostName(value);
                } else if ("httpCmdPort".equals(key)) {
                    node.setHttpCmdPort(Integer.valueOf(value));
                }
            }
            return node;
        } catch (RuntimeException e) {
            log.error("Error parse node , path:" + fullPath);
            throw e;
        }
    }

    public static String getFullPath(Node node) {
        StringBuilder path = new StringBuilder();

        path.append(getRootPath(node.getClusterName()))
                .append("/")
                .append(node.getNodeType())
                .append("/")
                .append(node.getNodeType())
                .append(":\\\\")
                .append(node.getIp());

        if (node.getPort() != null && node.getPort() != 0) {
            path.append(":").append(node.getPort());
        }

        path.append("?")
                .append("nodeGroup=")
                .append(node.getNodeGroup())
                .append("&clusterName=")
                .append(node.getClusterName());
        if (node.getThreads() != 0) {
            path.append("&threads=")
                    .append(node.getThreads());
        }

        path.append("&identity=")
                .append(node.getIdentity())
                .append("&createTime=")
                .append(node.getCreateTime())
                .append("&isAvailable=")
                .append(node.isAvailable())
                .append("&hostName=")
                .append(node.getHostName());

        if (node.getHttpCmdPort() != null) {
            path.append("&httpCmdPort=").append(node.getHttpCmdPort());
        }

        return path.toString();
    }

    public static String getRealRegistryAddress(String registryAddress) {
        if (StringUtils.isEmpty(registryAddress)) {
            throw new IllegalArgumentException("registryAddress is null！");
        }
        if (registryAddress.startsWith("zookeeper://")) {
            return registryAddress.replace("zookeeper://", "");
        } else if (registryAddress.startsWith("redis://")) {
            return registryAddress.replace("redis://", "");
        } else if (registryAddress.startsWith("multicast://")) {
            return registryAddress.replace("multicast://", "");
        }
        throw new IllegalArgumentException("Illegal registry protocol");
    }

}
