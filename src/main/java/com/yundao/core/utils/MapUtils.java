package com.yundao.core.utils;

import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.common.collect.Maps;

/**
 * map工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class MapUtils {

	private static final MapSplitter MAP_SPLITTER = Splitter.on("&").withKeyValueSeparator("=");

	/**
	 * 转化成map，格式key1=value1&key2=value2
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> toMap(String str) {
		if (BooleanUtils.isBlank(str)) {
			return null;
		}
		return MAP_SPLITTER.split(str);
	}

	/**
	 * 转换map中的值类型
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> cast(Map<String, String> map) {
		int size = map != null ? map.size() : 0;
		Map<String, T> result = Maps.newHashMapWithExpectedSize(size);
		if (size != 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				result.put(entry.getKey(), (T) entry.getValue());
			}
		}
		return result;
	}

}
