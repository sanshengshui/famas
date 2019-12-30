package com.grozacloud.famas.common.config;

/**
 * Define the key for a certain config item using system property,
 *  and provide the default value for that config item.
 *
 * @author james mu
 * @date 2019/12/30 14:13
 */
public class Configs {

    /** Netty epoll switch */
    public static final String NETTY_EPOLL_SWITCH                    = "famas.netty.epoll.switch";
    public static final String NETTY_EPOLL_SWITCH_DEFAULT            = "true";

    /** Netty epoll level trigger enabled */
    public static final String NETTY_EPOLL_LT                        = "famas.netty.epoll.lt";
    public static final String NETTY_EPOLL_LT_DEFAULT                = "true";


    /** TCP_NODELAY option */
    public static final String TCP_NODELAY                           = "bolt.tcp.nodelay";
    public static final String TCP_NODELAY_DEFAULT                   = "true";

    /** TCP SO_BACKLOG option */
    public static final String TCP_SO_BACKLOG                        = "famas.tcp.so.backlog";
    public static final String TCP_SO_BACKLOG_DEFAULT                = "1024";

    /** TCP SO_REUSEADDR option */
    public static final String TCP_SO_REUSEADDR                      = "famas.tcp.so.reuseaddr";
    public static final String TCP_SO_REUSEADDR_DEFAULT              = "true";

    /** TCP SO_KEEPALIVE option */
    public static final String TCP_SO_KEEPALIVE                      = "bolt.tcp.so.keepalive";
    public static final String TCP_SO_KEEPALIVE_DEFAULT              = "true";

    /** Netty ioRatio option*/
    public static final String NETTY_IO_RATIO                        = "bolt.netty.io.ratio";
    public static final String NETTY_IO_RATIO_DEFAULT                = "70";

    /** Netty buffer allocator, enabled as default */
    public static final String NETTY_BUFFER_POOLED                   = "bolt.netty.buffer.pooled";
    public static final String NETTY_BUFFER_POOLED_DEFAULT           = "true";
}
