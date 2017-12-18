package com.yundao.core.ed.config;

import com.yundao.core.config.AbstractFileConfig;
import com.yundao.core.disconf.common.DownloadFileBean;

/**
 * 加解密配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class EDFileConfig extends AbstractFileConfig {

    private static final String FILE = "classpath*:config/ed/ed.properties";

    static {
        load(FILE, getKeyPrefix());
        reload();
    }

    /**
     * 获取指定键的值
     *
     * @param config
     * @return
     */
    public static String getValue(EDConfigEnum config) {
        return AbstractFileConfig.getValue(getKeyPrefix() + config.getKey(), null);
    }

    /**
     * 获取指定键的值
     *
     * @param key 键
     * @return 键值
     */
    public static String getValue(String key) {
        return AbstractFileConfig.getValue(getKeyPrefix() + key, null);
    }

    /**
     * 获取配置文件中指定key的值，若==null则返回默认值
     *
     * @param config
     * @param defaultValue
     * @return
     */
    public static String getValue(EDConfigEnum config, String defaultValue) {
        return AbstractFileConfig.getValue(getKeyPrefix() + config.getKey(), defaultValue);
    }

    /**
     * 获取前辍
     *
     * @return
     */
    public static String getKeyPrefix() {
        return "ed#";
    }

    /**
     * 获取配置文件的路径
     *
     * @return
     */
    public static String getFile() {
        return FILE;
    }

    /**
     * 重新加载
     */
    public static void reload() {
        reload(DownloadFileBean.getDownloadPath() + "/ed.properties", getKeyPrefix());
        EDConfigEnum.reloadDynamic();
    }
}
