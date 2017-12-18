package com.yundao.core.utils;

import java.util.List;

/**
 * 字符串工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class StringUtils {

	/**
	 * 转集合为字符串
	 * 
	 * @param lists
	 * @return
	 */
	public static <T> String toString(List<T> lists) {
		StringBuilder result = new StringBuilder();
		int size = lists != null ? lists.size() : 0;
		for (int i = 0; i < size; i++) {
			result.append(lists.get(i));
			if (i != size - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}

	/**
	 * 转数组为字符串
	 * 
	 * @param arrays
	 * @return
	 */
	public static <T> String toString(T[] arrays) {
		StringBuilder result = new StringBuilder();
		int size = arrays != null ? arrays.length : 0;
		for (int i = 0; i < size; i++) {
			result.append(arrays[i]);
			if (i != size - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}

	/**
	 * 转义内容 <code>< ></code>
	 * 
	 * @param content
	 * @return
	 */
	public static String escapeHtml(String content) {
		if (BooleanUtils.isBlank(content)) {
			return content;
		}
		StringBuilder result = new StringBuilder(content.length() * 2);
		int length = content.length();
		for (int i = 0; i < length; i++) {
			char c = content.charAt(i);
			switch (c) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			// case '&':
			// result.append("&amp;");
			// break;
			// case '"':
			// result.append("&quot;");
			// break;
			// case 10: change line
			// case 13: enter
			// break;
			default:
				result.append(c);
			}
		}
		return result.toString();
	}
}
