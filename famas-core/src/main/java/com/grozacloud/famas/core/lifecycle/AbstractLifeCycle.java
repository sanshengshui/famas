package com.grozacloud.famas.core.lifecycle;

import com.grozacloud.famas.common.exception.LifeCycleException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author james mu
 * @date 2019/12/29 19:51
 */
public abstract class AbstractLifeCycle implements LifeCycle {

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    @Override
    public void startup() throws LifeCycleException {
        if (isStarted.compareAndSet(false, true)) {
            return;
        }
        throw new LifeCycleException("this component has started");
    }

    @Override
    public void shutdown() throws LifeCycleException {
        if (isStarted.compareAndSet(true, false)) {
            return;
        }
        throw new LifeCycleException("this component has closed");
    }

    @Override
    public boolean isStarted() {
        return isStarted.get();
    }
}
