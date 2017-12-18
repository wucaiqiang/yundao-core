package com.yundao.core.config.system;

import com.yundao.core.config.AbstractFileConfig;

/**
 * 系统配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class SystemFileConfig extends AbstractFileConfig {

    private static final String FILE = "config/properties/system/system.properties";

    static {
        load(FILE, getKeyPrefix());
    }

    /**
     * 获取指定键的值
     *
     * @param key 键
     * @return 配置值
     */
    public static String getValue(String key) {
        return AbstractFileConfig.getValue(getKeyPrefix() + key, null);
    }

    /**
     * 获取配置文件中指定key的值，若==null则返回默认值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 配置值
     */
    public static String getValue(String key, String defaultValue) {
        return AbstractFileConfig.getValue(getKeyPrefix() + key, defaultValue);
    }

    /**
     * 获取前辍
     *
     * @return 文件键前辍
     */
    public static String getKeyPrefix() {
        return "system#";
    }

    /**
     * 获取配置文件的路径
     *
     * @return 文件的路径
     */
    public static String getFile() {
        return FILE;
    }
}
