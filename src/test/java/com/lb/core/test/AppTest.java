package com.lb.core.test;

import com.lb.core.cluster.Node.Node;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by libo on 2017/4/14.
 */
@Slf4j
public class AppTest {
    public static void main(String[] args) {
        Node node = new Node();
        node.setIp("172.168.1.1");
        node.setNodeGroup("1111111");
        log.info("---------{}", node);
    }
}
