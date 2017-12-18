package com.yundao.core.log.config;

import com.yundao.core.config.AbstractFileConfig;
import com.yundao.core.config.common.CommonConfigEnum;
import com.yundao.core.utils.ConfigUtils;

/**
 * redis配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class RedisFileConfig extends AbstractFileConfig {

    private static final String FILE = "classpath*:cache/*.properties";

    static {
        // 加载指定位置的文件
        String reloadFile = ConfigUtils.getValue(CommonConfigEnum.RELOAD_CACHE_DIR.getKey());
        reload(reloadFile, getKeyPrefix());

        // 加载项目中的文件
        reload(FILE, getKeyPrefix());
    }

    /**
     * 获取指定键并与环境相匹配的值
     *
     * @param config
     * @return
     */
    public static String getValue(String config) {
        return getValue(config, null);
    }

    /**
     * 获取指定键并与环境相匹配的值，若==null则返回默认值
     *
     * @param config
     * @param defaultValue
     * @return
     */
    public static String getValue(String config, String defaultValue) {
        return AbstractFileConfig.getValue(getKeyPrefix() + config, defaultValue);
    }

    /**
     * 获取前辍
     *
     * @return
     */
    public static String getKeyPrefix() {
        return "redis#";
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
