package com.grozacloud.famas.mqtt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.security.cert.X509Certificate;


/**
 * @author james mu
 * @date 2019/12/30 15:15
 */
@Slf4j
public class MqttRemotingHandler extends ChannelInboundHandlerAdapter
        implements GenericFutureListener<Future<? super Void>> {

    private volatile boolean connected;
    private volatile InetSocketAddress address;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.trace("Processing msg: {}", msg);
        try {
            if (msg instanceof MqttMessage) {
               processMqttMsg(ctx, (MqttMessage) msg);
            } else {
                ctx.close();
            }
        } finally {
            ReferenceCountUtil.safeRelease(msg);
        }
    }

    private void processMqttMsg(ChannelHandlerContext ctx, MqttMessage msg) {
        address = (InetSocketAddress) ctx.channel().remoteAddress();
        if (msg.fixedHeader() == null) {
            log.info("[{}:{}] Invalid message received", address.getHostName(), address.getPort());
            processDisconnect(ctx);
            return;
        }
        switch (msg.fixedHeader().messageType()) {
            case CONNECT:
                break;
            case PUBLISH:
                break;
            case SUBSCRIBE:
                break;
            case UNSUBSCRIBE:
                break;
            case PINGREQ:
                break;
            case DISCONNECT:
                break;
            default:
                break;
        }
    }

    private void processDisconnect(ChannelHandlerContext ctx) {
        ctx.close();
    }

    private MqttConnAckMessage createMqttConnAckMsg(MqttConnectReturnCode returnCode) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader mqttConnAckVariableHeader =
                new MqttConnAckVariableHeader(returnCode, true);
        return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
    }

    private void processConnect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        log.info("Processing connect msg for client: {}!", msg.payload().clientIdentifier());
        processAuthTokenConnect(ctx, msg);
    }

    private void processX509CertConnect(ChannelHandlerContext ctx, X509Certificate cert) {
        ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_ACCEPTED));
        connected = true;
    }

    private void processAuthTokenConnect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        String userName = msg.payload().userName();
        log.info("Processing connect msg for client with user name: {}!", userName);
        if (StringUtils.isEmpty(userName)) {
            ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD));
            ctx.close();
        } else {
            ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_ACCEPTED));
            connected = true;
        }
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }
}
