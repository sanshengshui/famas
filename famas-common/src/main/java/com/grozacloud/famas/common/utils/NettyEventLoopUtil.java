package com.grozacloud.famas.common.utils;

import com.grozacloud.famas.common.config.ConfigManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ThreadFactory;

/**
 * @author james mu
 * @date 2019/12/30 13:57
 */
public class NettyEventLoopUtil {

    /**
     * check whether epoll enabled, and it would not be changed during runtime;
     */
    private static boolean epollEnabled = ConfigManager.netty_epoll() && Epoll.isAvailable();

    /**
     * Create the right event loop according to current platform and system property,
     *  fallback to NIO when epoll not enabled.
     * @param nThreads
     * @param threadFactory
     * @return
     */
    public static EventLoopGroup newEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        return epollEnabled ? new EpollEventLoopGroup(nThreads, threadFactory)
                : new NioEventLoopGroup(nThreads, threadFactory);
    }

    /**
     * a ServerSocketChannel class suitable for the given EventLoopGroup implementation
     * @return
     */
    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        return epollEnabled ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    /**
     * a SocketChannel class suitable for the given EventLoopGroup implementation
     * @return
     */
    public static Class<? extends SocketChannel> getClientSocketChannelClass() {
        return epollEnabled ? EpollSocketChannel.class : NioSocketChannel.class;
    }

    /**
     * use {@link EpollMode#LEVEL_TRIGGERED} for server bootstrap if level trigger enabled by system properties,
     *  otherwise use {@link EpollMode#EDGE_TRIGGERED}
     *
     * @param serverBootstrap server bootstrap
     */
    public static void enableTriggeredMode(ServerBootstrap serverBootstrap) {
        if (epollEnabled) {
            if (ConfigManager.netty_epoll_lt_enabled()) {
                serverBootstrap.childOption(EpollChannelOption.EPOLL_MODE,
                        EpollMode.LEVEL_TRIGGERED);
            } else {
                serverBootstrap
                        .childOption(EpollChannelOption.EPOLL_MODE, EpollMode.EDGE_TRIGGERED);
            }
        }
    }


}
