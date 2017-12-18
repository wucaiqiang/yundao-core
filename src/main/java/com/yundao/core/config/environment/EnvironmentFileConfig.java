package com.yundao.core.config.environment;

import com.yundao.core.config.AbstractFileConfig;

/**
 * 环境配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class EnvironmentFileConfig extends AbstractFileConfig {

    private static final String ENVIRONMENT = "environment";
    private static final String FILE = "classpath*:config/properties/system/environment.properties";
    private static String environmentValue = null;

    static {
        load(FILE, getKeyPrefix());
        environmentValue = "." + getDirectValue(ENVIRONMENT);

        // 重新加载指定位置的文件
        // String reloadFile = CommonFileConfig.getValue(CommonConfigEnum.RELOAD_ENVIRONMENT_DIR);
        // reload(reloadFile, getKeyPrefix());
    }

    /**
     * 直接获取配置文件中指定key的值，若==null则返回默认值
     *
     * @param config
     * @return
     */
    public static String getDirectValue(String config) {
        return AbstractFileConfig.getValue(getKeyPrefix() + config);
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
        if (!ENVIRONMENT.equals(config)) {
            config += environmentValue;
        }
        return AbstractFileConfig.getValue(getKeyPrefix() + config, defaultValue);
    }

    /**
     * 获取前辍
     *
     * @return
     */
    public static String getKeyPrefix() {
        return "environment#";
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
     * 是否正式环境
     *
     * @return true:是 false:否
     */
    public static boolean isProduct() {
        return "2".equals(getDirectValue(ENVIRONMENT));
    }

}
