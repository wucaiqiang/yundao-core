package com.yundao.core.config.system;

import com.yundao.core.config.AbstractFileConfig;
import com.yundao.core.threadlocal.ThreadLocalUtils;

/**
 * 系统名字配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class SystemNameFileConfig extends AbstractFileConfig {

    private static final String FILE = "classpath*:system/systemName.properties";

    static {
        load(FILE, getKeyPrefix());
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return AbstractFileConfig.getValue(getKeyPrefix() + key, null);
    }

    /**
     * 获取当前上下文中系统的名字
     *
     * @return
     */
    public static String getValue() {
        String key = ThreadLocalUtils.getRequestCommonParams().getId();
        return AbstractFileConfig.getValue(getKeyPrefix() + key, null);
    }

    /**
     * 获取前辍
     *
     * @return
     */
    public static String getKeyPrefix() {
        return "systemName#";
    }

    /**
     * 获取配置文件的路径
     *
     * @return
     */
    public static String getFile() {
        return FILE;
    }
}