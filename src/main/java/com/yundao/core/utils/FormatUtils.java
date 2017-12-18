package com.yundao.core.utils;

import java.text.MessageFormat;

/**
 * 格式化工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class FormatUtils {

	/**
	 * 格式化内容
	 * 
	 * @param content
	 * @param params
	 * @return
	 */
	public static String format(String content, Object... params) {
		if (BooleanUtils.isBlank(content) || (BooleanUtils.isEmpty(params))) {
			return content;
		}
		return MessageFormat.format(content, params);
	}
}
