package com.grozacloud.famas.common.exception;

/**
 * @author james mu
 * @date 2019/12/29 19:43
 */
public class LifeCycleException extends RuntimeException {

    private static final long serialVersionUID = -5581833793111988391L;

    public LifeCycleException(String message) {
        super(message);
    }
}
