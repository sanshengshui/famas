package com.grozacloud.famas.core.lifecycle;

import com.grozacloud.famas.common.exception.LifeCycleException;

/**
 * @author james mu
 * @date 2019/12/29 19:41
 */
public interface LifeCycle {

    void startup() throws LifeCycleException;

    void shutdown() throws LifeCycleException;

    boolean isStarted();
}
