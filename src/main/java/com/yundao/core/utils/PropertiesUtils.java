package com.yundao.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import com.google.common.io.Closeables;

/**
 * properties 工具类
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class PropertiesUtils {

	/**
	 * 转化为map
	 * 
	 * @param prop
	 * @return
	 */
	public static Map<String, String> getToMap(Properties prop) {
		return getToMap(prop, null);
	}

	/**
	 * 转化为map，若前辍不为空则加到map中的key
	 * 
	 * @param prop
	 * @param keyPrefix
	 * @return
	 */
	public static Map<String, String> getToMap(Properties prop, String keyPrefix) {
		if (prop == null) {
			return null;
		}
		if (keyPrefix == null) {
			keyPrefix = "";
		}

		Map<String, String> result = new HashMap<String, String>();
		Set<Entry<Object, Object>> set = prop.entrySet();
		for (Entry<Object, Object> entry : set) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			result.put(keyPrefix + key, value);
		}
		return result;
	}

	/**
	 * 加载配置文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static Properties load(String file) throws IOException {
		if (StringUtils.isBlank(file)) {
			return null;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		}
		catch (Exception e) {
			is = ClassUtils.getDefaultClassLoader().getResourceAsStream(file);
		}
		return load(is, true);
	}

	/**
	 * 加载配置文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Properties load(File file) throws IOException {
		if (file == null) {
			return null;
		}
		return load(new FileInputStream(file), true);
	}

	/**
	 * 加载配置文件
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Properties load(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		return load(is, true);
	}

	/**
	 * 加载配置文件，isClose是否关闭流
	 * 
	 * @param is
	 * @param isClose
	 * @return
	 * @throws IOException
	 */
	public static Properties load(InputStream is, boolean isClose) throws IOException {
		if (is == null) {
			return null;
		}
		Properties props = new Properties();
		try {
			props.load(is);
		}
		finally {
			if (isClose) {
				Closeables.closeQuietly(is);
			}
		}
		return props;
	}

}
