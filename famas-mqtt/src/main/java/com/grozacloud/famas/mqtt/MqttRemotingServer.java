package com.grozacloud.famas.mqtt;

import com.grozacloud.famas.core.server.AbstractRemotingServer;


/**
 * @author james mu
 * @date 2019/11/13 11:27
 */
public class MqttRemotingServer extends AbstractRemotingServer {

    public MqttRemotingServer(int port) {
        super(port);
    }

    public MqttRemotingServer(String ip, int port) {
        super(ip, port);
    }

    @Override
    protected void doInit() {

    }

    @Override
    protected boolean doStart() throws InterruptedException {
        return false;
    }

    @Override
    protected boolean doStop() {
        return false;
    }

    @Override
    public String ip() {
        return null;
    }

    @Override
    public int port() {
        return 0;
    }
}
