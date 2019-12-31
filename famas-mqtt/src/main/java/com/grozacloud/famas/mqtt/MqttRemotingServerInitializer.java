package com.grozacloud.famas.mqtt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

/**
 * @author james mu
 * @date 2019/12/31 17:12
 */
public class MqttRemotingServerInitializer extends ChannelInitializer<SocketChannel> {

    private final int maxPayloadSize;

    public MqttRemotingServerInitializer(int maxPayloadSize) {
        this.maxPayloadSize = maxPayloadSize;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", new MqttDecoder(maxPayloadSize));
        pipeline.addLast("encoder", MqttEncoder.INSTANCE);
        MqttRemotingHandler handler = new MqttRemotingHandler();
        pipeline.addLast(handler);
        socketChannel.closeFuture().addListener(handler);
    }
}
