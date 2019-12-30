package com.grozacloud.famas.core.server;

import com.grozacloud.famas.common.exception.LifeCycleException;
import com.grozacloud.famas.common.log.FamasLoggerFactory;
import com.grozacloud.famas.core.lifecycle.AbstractLifeCycle;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

/**
 * @author james mu
 * @date 2019/12/29 19:38
 */
public abstract class AbstractRemotingServer extends AbstractLifeCycle implements  RemotingServer{

    private static final Logger logger = FamasLoggerFactory.getLogger("CommonDefault");

    private String ip;

    private int port;

    public AbstractRemotingServer(int port) {
        this(new InetSocketAddress(port).getAddress().getHostAddress(), port);
    }

    public AbstractRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void startup() throws LifeCycleException {
        super.startup();

        try {
            doInit();
            logger.warn("Prepare to start server on port {}", port);
            if (doStart()) {
                logger.warn("Server started on port{}", port);
            } else {
                logger.warn("Failed starting server on port {}", port);
                throw new LifeCycleException("Failed starting server on port: " + port);
            }
        } catch (Throwable t) {
           this.shutdown();
           throw new IllegalStateException("ERROR: Failed to start the Server!", t);
        }

    }

    @Override
    public void shutdown() throws LifeCycleException {
        super.shutdown();
        if (!doStop()) {
            throw new LifeCycleException("doStop fail");
        }
    }

    protected abstract void doInit();

    protected abstract boolean doStart() throws InterruptedException;

    protected  abstract boolean doStop();
}
