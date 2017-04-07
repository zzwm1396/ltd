package com.lb.core.registry;

import com.lb.core.cluster.Node;
import com.lb.core.enums.NotifyEvent;

import java.util.List;

/**
 * 监听接口
 * Created by libo on 2017/4/7.
 */
public interface NotifyListener {

    void notify(NotifyEvent event, List<Node> nodes);

}
