package com.yundao.core.code.config;

import java.util.Map;

import com.yundao.core.config.AbstractFileConfig;

/**
 * 编码配置文件
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class CodeFileConfig extends AbstractFileConfig {

	private static final String FILE = "classpath*:config/code/*.properties";
	private static final String RELOAD_FILE = "classpath*:config/properties/code/*.properties";

	static {
		load(FILE, getKeyPrefix());
		reload(RELOAD_FILE, getKeyPrefix());
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param config
	 * @return
	 */
	public static String getValue(String config) {
		return AbstractFileConfig.getValue(getKeyPrefix() + config, null);
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param config
	 * @param params
	 * @return
	 */
	public static String getValue(String config, Map<String, String> params) {
		String result = AbstractFileConfig.getValue(getKeyPrefix() + config, null);
		return AbstractFileConfig.replacePlaceholder(result, params);
	}

	/**
	 * 获取配置文件中指定key的值，若==null则返回默认值
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
		return "code#";
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
