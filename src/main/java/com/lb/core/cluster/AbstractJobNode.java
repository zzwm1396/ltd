package com.lb.core.cluster;

import com.lb.core.AppContext;
import com.lb.core.commons.utils.GenericsUtils;
import com.lb.core.factory.JobNodeConfigFactory;
import com.lb.core.factory.NodeFactory;
import com.lb.core.listener.MasterChangeListener;
import com.lb.core.listener.NodeChangeListener;
import com.lb.core.registry.Registry;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Created by libo on 2017/4/7.
 */
public class AbstractJobNode<T extends Node, Context extends AppContext> implements JobNode{


    protected Registry registry;

    protected T node;

    protected Config config;

    protected Context appContext;

    private List<NodeChangeListener> nodeChangeListeners;

    private List<MasterChangeListener> masterChangeListeners;

    protected AtomicBoolean started = new AtomicBoolean(false);

    public AbstractJobNode(){
        appContext = getAppContext();
        node = NodeFactory.create(getNodeClass());
        config = JobNodeConfigFactory.getDefaultConfig();
        config.setNodeType(node.getNodeType());

    }

    private Context getAppContext() {
        try {
            return ((Class<Context>)
                    GenericsUtils.getSuperClassGenericType(this.getClass(), 1))
                    .newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<T> getNodeClass() {
        return (Class<T>)
                GenericsUtils.getSuperClassGenericType(this.getClass(), 0);
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
