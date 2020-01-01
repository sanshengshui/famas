package com.grozacloud.famas.mqtt;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author james mu
 * @date 2019/11/13 11:27
 */
@Service
@ConditionalOnProperty(prefix = "mqtt", value = "enabled", havingValue = "true", matchIfMissing = false)
public class MqttRemotingServer {

    private static final String V1 = "v1";
    private static final String DEVICE = "device";

    @Value("${mqtt.bind_address}")
    private String host;
    @Value("${mqtt.bind_port}")
    private Integer port;

    @Value("${mqtt.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${mqtt.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${mqtt.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${mqtt.netty.max_payload_size}")
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
