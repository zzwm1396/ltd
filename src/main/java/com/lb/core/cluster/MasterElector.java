package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.constant.EcTopic;
import com.lb.core.ec.EventInfo;
import com.lb.core.listener.MasterChangeListener;
import com.lb.core.logger.Logger;
import com.lb.core.logger.LoggerFactory;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 节点选举者
 * Created by libo on 2017/4/10.
 */

public class MasterElector {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterElector.class);

    private AppContext appContext;

    private List<MasterChangeListener> listeners;
    private Node master;
    private ReentrantLock lock  = new ReentrantLock();

    public MasterElector(AppContext appContext){
        this.appContext = appContext;
    }

    /**
     * 添加监听节点列表
     * @param masterChangeListeners 需要添加的监听节点集合
     */
    public void addMasterChangeLinstener(List<MasterChangeListener> masterChangeListeners){
        if (listeners == null)
            listeners = new CopyOnWriteArrayList<MasterChangeListener>();

        if (!masterChangeListeners.isEmpty()){
            listeners.addAll(masterChangeListeners);
        }
    }

    public void addNodes(List<Node> nodes){
        lock.lock();
        try {
            Node newMaster = null;
            for (Node node : nodes) {
                if (newMaster == null)
                    newMaster = node;
                else {
                    if (newMaster.getCreateTime() > node.getCreateTime()){
                        newMaster = node;
                    }
                }
            }
            addNode(newMaster);
        }finally {
            lock.unlock();
        }
    }

    private void addNode(Node newNode){
        lock.lock();
        try{
            if (master == null){
                master = newNode;
                notifyListener();
            } else {
                if (master.getCreateTime() > newNode.getCreateTime()){
                    master = newNode;
                    notifyListener();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void notifyListener() {
        boolean isMaster = false;
        if (appContext.getConfig().getIdentity().equals(master.getIdentity())) {
            LOGGER.info("Current node become the master node:{}", master);
            isMaster = true;
        } else {
            LOGGER.info("Master node is :{}", master);
            isMaster = false;
        }

        if (listeners != null) {
            for (MasterChangeListener masterChangeListener : listeners) {
                try {
                    masterChangeListener.change(master, isMaster);
                } catch (Throwable t) {
                    LOGGER.error("MasterChangeListener notify error!", t);
                }
            }
        }
        EventInfo eventInfo = new EventInfo(EcTopic.MASTER_CHANGED);
        eventInfo.setParam("master", master);
        appContext.getEventCenter().publishSync(eventInfo);
    }

}























