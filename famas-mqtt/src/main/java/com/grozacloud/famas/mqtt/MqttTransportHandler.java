package com.grozacloud.famas.mqtt;

import com.grozacloud.famas.common.log.FamasLoggerFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;

/**
 * @author james mu
 * @date 2019/12/30 15:15
 */
@ChannelHandler.Sharable
public class MqttTransportHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = FamasLoggerFactory.getLogger("MqttTransportHandler");
}
