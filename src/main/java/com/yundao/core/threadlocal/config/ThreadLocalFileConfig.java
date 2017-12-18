package com.yundao.core.threadlocal.config;

import com.yundao.core.config.AbstractFileConfig;

/**
 * 本地线程配置文件
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class ThreadLocalFileConfig extends AbstractFileConfig {

	private static final String FILE = "classpath*:config/threadlocal/threadlocal.properties";

	static {
		load(FILE, getKeyPrefix());
	}

	/**
	 * 获取指定键的值
	 * 
	 * @param config
	 * @return
	 */
	public static String getValue(ThreadLocalConfigEnum config) {
		return AbstractFileConfig.getValue(getKeyPrefix() + config.getKey(), null);
	}

	/**
	 * 获取配置文件中指定key的值，若==null则返回默认值
	 * 
	 * @param config
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(ThreadLocalConfigEnum config, String defaultValue) {
		return AbstractFileConfig.getValue(getKeyPrefix() + config.getKey(), defaultValue);
	}

	/**
	 * 获取前辍
	 * 
	 * @return
	 */
	public static String getKeyPrefix() {
		return "threadlocal#";
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
