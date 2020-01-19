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
import java.util.ArrayList;
import java.util.List;


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
                processConnect(ctx, (MqttConnectMessage) msg);
                break;
            case PUBLISH:
                processPublish(ctx, (MqttPublishMessage) msg);
                break;
            case SUBSCRIBE:
                processSubscribe(ctx, (MqttSubscribeMessage) msg);
                break;
            case UNSUBSCRIBE:
                processUnsubscribe(ctx, (MqttUnsubscribeMessage) msg);
                break;
            case PINGREQ:
                if (checkConnected(ctx)) {
                    ctx.writeAndFlush(new MqttMessage(new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0)));
                }
                break;
            case DISCONNECT:
                if (checkConnected(ctx)) {
                    processDisconnect(ctx);
                }
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

    private void processPublish(ChannelHandlerContext ctx, MqttPublishMessage mqttMsg) {
        if (checkConnected(ctx)) {
            return;
        }
        String topicName = mqttMsg.variableHeader().topicName();
        int msgId = mqttMsg.variableHeader().packetId();
        log.trace("Processing publish msg [{}][{}]!", topicName, msgId);

        if (topicName.startsWith(MqttTopics.BASE_GATEWAY_API_TOPIC)) {

        } else {
            processDevicePublish(ctx, mqttMsg, topicName, msgId);
        }
    }

    private void processDevicePublish(ChannelHandlerContext ctx, MqttPublishMessage mqttMsg, String topicName, int msgId) {

    }

    private void processSubscribe(ChannelHandlerContext ctx, MqttSubscribeMessage mqttMsg) {
        if (!checkConnected(ctx)) {
            return;
        }
        log.trace("Processing subscription [{}]!", mqttMsg.variableHeader().messageId());
        List<Integer> grantedQoSList = new ArrayList<>();
        ctx.writeAndFlush(createSubAckMessage(mqttMsg.variableHeader().messageId(), grantedQoSList));
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

    private void processUnsubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage mqttMsg) {
        if (!checkConnected(ctx)) {
            return;
        }
        log.trace("Processing subscription [{}]!", mqttMsg.variableHeader().messageId());
        ctx.writeAndFlush(createUnSubAckMessage(mqttMsg.variableHeader().messageId()));
    }

    private MqttMessage createUnSubAckMessage(int msgId) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_LEAST_ONCE, false, 0 );
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(msgId);
        return new MqttMessage(mqttFixedHeader, mqttMessageIdVariableHeader);
    }

    private static MqttSubAckMessage createSubAckMessage(Integer msgId, List<Integer> grantedQoSList) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(msgId);
        MqttSubAckPayload mqttSubAckPayload = new MqttSubAckPayload(grantedQoSList);
        return new MqttSubAckMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubAckPayload);
    }

    private boolean checkConnected(ChannelHandlerContext ctx) {
        if (connected) {
            return true;
        } else {
            ctx.close();
            return false;
        }
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }
}
