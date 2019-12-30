package com.grozacloud.famas.common.config;

/**
 * @author james mu
 * @date 2019/12/30 14:16
 */
public class ConfigManager {

    public static boolean tcp_nodelay() {
        return getBool(Configs.TCP_NODELAY, Configs.TCP_NODELAY_DEFAULT);
    }

    public static boolean netty_epoll() {
        return getBool(Configs.NETTY_EPOLL_SWITCH, Configs.NETTY_EPOLL_SWITCH_DEFAULT);
    }

    public static boolean netty_epoll_lt_enabled() {
        return getBool(Configs.NETTY_EPOLL_LT, Configs.NETTY_EPOLL_LT_DEFAULT);
    }


    public static int tcp_so_backlog() {
        return getInt(Configs.TCP_SO_BACKLOG, Configs.TCP_SO_BACKLOG_DEFAULT);
    }

    public static boolean tcp_so_reuseaddr() {
        return getBool(Configs.TCP_SO_REUSEADDR, Configs.TCP_SO_REUSEADDR_DEFAULT);
    }

    public static boolean tcp_so_keepalive() {
        return getBool(Configs.TCP_SO_KEEPALIVE, Configs.TCP_SO_KEEPALIVE_DEFAULT);
    }

    public static int netty_io_ratio() {
        return getInt(Configs.NETTY_IO_RATIO, Configs.NETTY_IO_RATIO_DEFAULT);
    }

    public static boolean netty_buffer_pooled() {
        return getBool(Configs.NETTY_BUFFER_POOLED, Configs.NETTY_BUFFER_POOLED_DEFAULT);
    }

    public static boolean getBool(String key, String defaultValue) {
        return Boolean.parseBoolean(System.getProperty(key, defaultValue));
    }

    public static int getInt(String key, String defaultValue) {
        return Integer.parseInt(System.getProperty(key, defaultValue));
    }
}
