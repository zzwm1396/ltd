package com.lb.core.cluster;

import com.lb.core.registry.Registry;
import com.sun.deploy.appcontext.AppContext;

/**
 *
 * Created by libo on 2017/4/7.
 */
public class AbstractJobNode<T extends Node, Context extends AppContext> implements JobNode{


    protected Registry registry;

    protected T node;

    protected Config config;



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
