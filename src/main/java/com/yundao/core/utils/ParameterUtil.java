/**
 * 
 */
package com.yundao.core.utils;

import java.util.Map;

import com.yundao.core.ed.Md5Utils;

/**
 * @author Jon Chiang
 * @date 2016年8月8日
 */
public class ParameterUtil {

	/**
	 * 
	 * @param params
	 * @return
	 */
	public static String getSign(Map<String, String> params) {
		StringBuilder content = new StringBuilder();
		params.forEach((key,value) ->{
			content.append(key);
			content.append("=");
			content.append(value);
			content.append("&");
		});
		params.remove("secret");
		return Md5Utils.md5(content.substring(0,content.length()-1));
	}
}
