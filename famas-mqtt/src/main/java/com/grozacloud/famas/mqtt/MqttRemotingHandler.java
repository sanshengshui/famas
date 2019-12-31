package com.grozacloud.famas.mqtt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * @author james mu
 * @date 2019/12/30 15:15
 */

public class MqttRemotingHandler extends ChannelInboundHandlerAdapter
        implements GenericFutureListener<Future<? super Void>> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }
}
