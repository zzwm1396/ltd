package com.lb.core.cluster.Node;

import com.lb.core.AppContext;
import com.lb.core.cluster.Config;
import com.lb.core.commons.utils.GenericsUtils;
import com.lb.core.listener.MasterChangeListener;
import com.lb.core.listener.NodeChangeListener;
import com.lb.core.registry.base.Registry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象节点
 * Created by libo on 2017/4/14.
 */
@Slf4j
public class AbstractNode<T extends Node, Context extends AppContext> implements NodeManger{

    protected Registry registry;
    protected T node;
    protected Config config;
    protected Context appContext;
    private List<NodeChangeListener> nodeChangeListeners;
    private List<MasterChangeListener> masterChangeListeners;
    protected AtomicBoolean started = new AtomicBoolean(false);

    private Context getAppContext(){
        try {
            return ((Class<Context>) GenericsUtils.getSuperClassGenericType(this.getClass(), 1)).newInstance();
        } catch (InstantiationException e){
            throw new RuntimeException(e);
        } catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public AbstractNode(){
        this.appContext = this.getAppContext();

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
