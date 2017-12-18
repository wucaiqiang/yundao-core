package com.yundao.core.log;

import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 日志工厂类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class LogFactory {

    private static AtomicLong errorCount = new AtomicLong(0);
    private static AtomicLong warnCount = new AtomicLong(0);
    private static Constructor<?> logConstructor = null;
    private static String[] log4j = {"org.apache.log4j.Logger", "com.yundao.core.log.Log4jLog"};
    private static String[] slf4j = {"org.slf4j.Logger", "com.yundao.core.log.Slf4jLog"};
    private static String[] commons = {"org.apache.commons.logging.LogFactory", "CommonsLog"};
    private static String[] jdk = {"java.util.logging.Logger", "JdkLog"};

    static {
        //tryConfigLog(log4j[0], log4j[1], logConstructor);
        tryConfigLog(slf4j[0], slf4j[1], logConstructor);
        tryConfigLog(commons[0], commons[1], logConstructor);
        tryConfigLog(jdk[0], jdk[1], logConstructor);

        if (logConstructor == null) {
            throw new NullPointerException("实例化日志异常，请配置第三方日志jar包（log4j）");
        }
    }

    /**
     * 获取日志接口
     *
     * @param clazz
     * @return
     */
    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    /**
     * 获取日志接口
     *
     * @param loggerName
     * @return
     */
    public static Log getLog(String loggerName) {
        try {
            return (Log) logConstructor.newInstance(loggerName);
        }
        catch (Throwable t) {
            throw new RuntimeException("获取日志异常, loggerName=" + loggerName, t);
        }
    }

    /**
     * 设置为log4j日志框架
     *
     * @return
     */
    public static synchronized boolean configLog4jLog() {
        return configLog(log4j[0], log4j[1]);
    }

    /**
     * 设置为slf4j日志框架
     *
     * @return
     */
    public static synchronized boolean configSlf4jLog() {
        return configLog(slf4j[0], slf4j[1]);
    }

    /**
     * 设置为commons日志框架
     *
     * @return
     */
    public static synchronized boolean configCommonsLog() {
        return configLog(commons[0], commons[1]);
    }

    /**
     * 设置为jdk日志框架
     *
     * @return
     */
    public static synchronized boolean configJdkLog() {
        return configLog(jdk[0], jdk[1]);
    }

    /**
     * 设置日志框架
     *
     * @param interfaceName
     * @param implName
     * @return
     */
    public static synchronized boolean configLog(String interfaceName, String implName) {
        return tryConfigLog(interfaceName, implName, null);
    }

    /**
     * 增加warn的数量
     */
    public static void incrementWarnCount() {
        warnCount.getAndIncrement();
    }

    /**
     * 增加error的数量
     */
    public static void incrementErrorCount() {
        errorCount.getAndIncrement();
    }

    /**
     * 获取当前warn的数量
     *
     * @return
     */
    public static long getWarnCount() {
        return warnCount.get();
    }

    /**
     * 获取当前error的数量
     *
     * @return
     */
    public static long getErrorCount() {
        return errorCount.get();
    }

    /**
     * 重置当前warn和error的数量
     */
    public static void resetCount() {
        warnCount.set(0);
        errorCount.set(0);
    }

    private static boolean tryConfigLog(String interfaceName, String implName, Constructor<?> constructor) {
        boolean result = false;
        if (constructor != null) {
            return result;
        }

        Constructor<?> implConstructor = null;
        try {
            ClassUtils.getClass(interfaceName);
            Class<?> implClass = ClassUtils.getClass(implName);
            implConstructor = implClass.getConstructor(new Class[]{String.class});
            Class<?> declareClass = implConstructor.getDeclaringClass();
            if (!Log.class.isAssignableFrom(declareClass)) {
                implConstructor = null;
            }

            try {
                if (null != implConstructor) {
                    implConstructor.newInstance(LogFactory.class.getName());
                }
            }
            catch (Throwable t) {
                implConstructor = null;
            }
        }
        catch (Throwable t) {
            implConstructor = null;
        }
        if (implConstructor != null) {
            logConstructor = implConstructor;
            result = true;
        }
        return result;
    }

}