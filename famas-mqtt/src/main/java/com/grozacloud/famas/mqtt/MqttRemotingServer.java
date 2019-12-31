package com.grozacloud.famas.mqtt;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author james mu
 * @date 2019/11/13 11:27
 */

public class MqttRemotingServer {

    private static final String V1 = "v1";

    private static final String DEVICE = "device";

    private String host;

    private Integer port;

    private String leakDetectorLevel;

    private Integer bossGroupThreadCount;

    private Integer workerGroupThreadCount;

    private Integer maxPayloadSize;

    private Channel serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    public void init() throws Exception {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);

        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MqttRemotingServerInitializer(maxPayloadSize));
        serverChannel = b.bind(host, port).sync().channel();
    }

    @PreDestroy
    public void shutdown() throws Exception {
        try {
            serverChannel.close().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
