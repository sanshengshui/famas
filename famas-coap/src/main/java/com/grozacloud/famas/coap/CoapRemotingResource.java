package com.grozacloud.famas.coap;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * @author james mu
 * @date 2020/1/2 17:09
 */
@Slf4j
public class CoapRemotingResource extends CoapResource {

    private final long timeout;

    public CoapRemotingResource(String name, long timeout) {
        super(name);
        this.timeout = timeout;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        super.handleGET(exchange);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        super.handlePOST(exchange);
    }
}
