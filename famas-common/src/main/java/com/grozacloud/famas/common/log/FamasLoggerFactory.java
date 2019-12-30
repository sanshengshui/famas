package com.grozacloud.famas.common.log;

import com.alipay.sofa.common.log.LoggerSpaceManager;
import com.grozacloud.famas.common.utils.StringUtils;
import org.slf4j.Logger;

import java.io.File;

/**
 * @author james mu
 * @date 2019/12/30 09:20
 */
public class FamasLoggerFactory {

    public static final String FAMAS_LOG_SPACE_PROPERTY = "famas.log.space";

    private static String FAMAS_LOG_SPACE = "com.grozacloud.remoting";

    public static final String LOG_PATH = "logging.path";
    public static final String LOG_PATH_DEFAULT = System.getProperty("user.home") + File.separator + "logs";
    public static final String CLIENT_LOG_LEVEL = "com.grozacloud.remoting.client.log.level";
    public static final String CLIENT_LOG_LEVEL_DEFAULT = "INFO";
    public static final String CLIENT_LOG_ENCODE = "com.grozacloud.remoting.client.log.encode";
    public static final String COMMON_ENCODE = "file.encoding";
    public static final String CLIENT_LOG_ENCODE_DEFAULT = "UTF-8";

    static {
        String logSpace = System.getProperty(FAMAS_LOG_SPACE_PROPERTY);
        if (null != logSpace && !logSpace.isEmpty()) {
            FAMAS_LOG_SPACE = logSpace;
        }

        String logPath = System.getProperty(LOG_PATH);
        if (StringUtils.isBlank(logPath)) {
            System.setProperty(LOG_PATH, LOG_PATH_DEFAULT);
        }

        String logLevel = System.getProperty(CLIENT_LOG_LEVEL);
        if (StringUtils.isBlank(logLevel)) {
            System.setProperty(CLIENT_LOG_LEVEL, CLIENT_LOG_ENCODE_DEFAULT);
        }

        String commonEncode = System.getProperty(COMMON_ENCODE);
        if (StringUtils.isNotBlank(commonEncode)) {
            System.setProperty(CLIENT_LOG_ENCODE, commonEncode);
        } else {
            String logEncode = System.getProperty(CLIENT_LOG_ENCODE);
            if (StringUtils.isBlank(logEncode)) {
                System.setProperty(CLIENT_LOG_ENCODE, CLIENT_LOG_ENCODE_DEFAULT);
            }
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return getLogger(clazz.getCanonicalName());
    }

    public static Logger getLogger(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return LoggerSpaceManager.getLoggerBySpace(name, FAMAS_LOG_SPACE);
    }
}
