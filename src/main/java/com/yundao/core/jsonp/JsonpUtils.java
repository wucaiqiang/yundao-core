package com.yundao.core.jsonp;

import com.yundao.core.utils.BooleanUtils;

/**
 * Jsonp注解
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class JsonpUtils {

	/**
	 * 转为jsonp的格式
	 * 
	 * @param jsonpName
	 * @param content
	 * @return
	 */
	public static String toJsonp(String jsonpName, String content) {
		String result = "";
		if (!BooleanUtils.isBlank(jsonpName) && !BooleanUtils.isBlank(content)) {
			result = jsonpName + "(" + content + ")";
		}
		return result;
	}
}
