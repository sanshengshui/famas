package com.grozacloud.famas.mqtt;

import com.grozacloud.famas.common.NamedThreadFactory;
import com.grozacloud.famas.common.config.ConfigManager;
import com.grozacloud.famas.common.log.FamasLoggerFactory;
import com.grozacloud.famas.common.utils.NettyEventLoopUtil;
import com.grozacloud.famas.core.server.AbstractRemotingServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import org.slf4j.Logger;

import java.net.InetSocketAddress;


/**
 * @author james mu
 * @date 2019/11/13 11:27
 */
public class MqttRemotingServer extends AbstractRemotingServer {

    private static final Logger logger = FamasLoggerFactory.getLogger("MqttRemotingServer");

    private ServerBootstrap bootstrap;

    private ChannelFuture channelFuture;

    private final EventLoopGroup bossGroup = NettyEventLoopUtil
                                                .newEventLoopGroup(1,
                                                        new NamedThreadFactory("Mqtt-netty-server-boss",
                                                                false));

    private static final EventLoopGroup workerGroup = NettyEventLoopUtil
                                                        .newEventLoopGroup(
                                                                Runtime.getRuntime().availableProcessors() * 2,
                                                                new NamedThreadFactory("Mqtt-netty-server-worker",
                                                                        true));

    public MqttRemotingServer(int port) {
        super(port);
    }

    public MqttRemotingServer(String ip, int port) {
        super(ip, port);
    }

    @Override
    protected void doInit() {
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup)
                .channel(NettyEventLoopUtil.getServerSocketChannelClass())
                .option(ChannelOption.SO_BACKLOG, ConfigManager.tcp_so_backlog())
                .option(ChannelOption.SO_REUSEADDR, ConfigManager.tcp_so_reuseaddr())
                .childOption(ChannelOption.TCP_NODELAY, ConfigManager.tcp_nodelay())
                .childOption(ChannelOption.SO_KEEPALIVE, ConfigManager.tcp_so_keepalive());

        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder", new MqttDecoder());
                pipeline.addLast("encoder", MqttEncoder.INSTANCE);
            }
        });
    }

    @Override
    protected boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress(ip(), port())).sync();
        return this.channelFuture.isSuccess();
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
