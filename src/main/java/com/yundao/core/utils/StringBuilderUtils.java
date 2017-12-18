package com.yundao.core.utils;

/**
 * StringBuilder工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class StringBuilderUtils {

	/**
	 * 删除最后一个字符
	 * 
	 * @param buffer
	 */
	public static void deleteLastChar(StringBuilder builder) {
		int length = 0;
		if (builder != null && (length = builder.length()) != 0) {
			builder.deleteCharAt(length - 1);
		}
	}

}