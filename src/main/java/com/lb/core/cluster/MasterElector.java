package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.cluster.Node.Node;
import com.lb.core.cluster.constant.EcTopic;
import com.lb.core.commons.utils.CollectionUtils;
import com.lb.core.ec.EventInfo;
import com.lb.core.listener.MasterChangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Master 选举
 * <p>
 * 选举思想:
 * 选出每种节点中的master, 通过每个节点的创建时间来决定 （创建时间最小的便是master, 即最早创建的是master）
 * 当master 挂掉之后, 要重新选举
 * </p>
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class MasterElector {
    private AppContext appContext;
    private List<MasterChangeListener> listeners;
    private Node master;
    private ReentrantLock lock = new ReentrantLock();

    public MasterElector(AppContext appContext) {
        this.appContext = appContext;
    }

    public void addMasterChangeListener(List<MasterChangeListener> masterChangeListeners) {
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<MasterChangeListener>();
        }
        if (CollectionUtils.isNotEmpty(masterChangeListeners)) {
            listeners.addAll(masterChangeListeners);
        }
    }

    public void addNodes(List<Node> nodes) {
        lock.lock();
        try {
            Node newMaster = null;
            for (Node node : nodes) {
                if (newMaster == null) {
                    newMaster = node;
                } else {
                    if (newMaster.getCreateTime() > node.getCreateTime()) {
                        newMaster = node;
                    }
                }
            }
            addNode(newMaster);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 当前节点是否是master节点
     */
    public boolean isCurrentMaster() {
        return master != null && master.getIdentity().equals(appContext.getConfig().getIdentity());
    }

    public void addNode(Node newNode) {
        lock.lock();
        try {
            if (master == null) {
                master = newNode;
                notifyListener();
            } else {
                if (master.getCreateTime() > newNode.getCreateTime()) {
                    master = newNode;
                    notifyListener();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeNode(List<Node> removedNodes) {
        lock.lock();
        try {
            if (master != null) {
                boolean masterRemoved = false;
                for (Node removedNode : removedNodes) {
                    if (master.getIdentity().equals(removedNode.getIdentity())) {
                        masterRemoved = true;
                    }
                }
                if (masterRemoved) {
                    // 如果挂掉的是master, 需要重新选举
                    List<Node> nodes = appContext.getSubscribedNodeManager().
                            getNodeList(appContext.getConfig().getNodeType(), appContext.getConfig().getNodeGroup());
                    if (CollectionUtils.isNotEmpty(nodes)) {
                        Node newMaster = null;
                        for (Node node : nodes) {
                            if (newMaster == null) {
                                newMaster = node;
                            } else {
                                if (newMaster.getCreateTime() > node.getCreateTime()) {
                                    newMaster = node;
                                }
                            }
                        }
                        master = newMaster;
                        notifyListener();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void notifyListener() {
        boolean isMaster = false;
        if (appContext.getConfig().getIdentity().equals(master.getIdentity())) {
            log.info("Current node become the master node:{}", master);
            isMaster = true;
        } else {
            log.info("Master node is :{}", master);
            isMaster = false;
        }

        if (listeners != null) {
            for (MasterChangeListener masterChangeListener : listeners) {
                try {
                    masterChangeListener.change(master, isMaster);
                } catch (Throwable t) {
                    log.error("MasterChangeListener notify error!", t);
                }
            }
        }
        EventInfo eventInfo = new EventInfo(EcTopic.MASTER_CHANGED);
        eventInfo.setParam("master", master);
        appContext.getEventCenter().publishSync(eventInfo);
    }

}
