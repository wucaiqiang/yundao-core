package com.yundao.core.ed;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.utils.BooleanUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * md5工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class Md5Utils {

	/**
	 * 获取md5加密值
	 * 
	 * @param value
	 * @return
	 */
	public static String md5(String value) {
		return md5(value, null);
	}

	/**
	 * 获取md5加密值
	 * 
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String md5(String value, String charset) {
		if (BooleanUtils.isBlank(value)) {
			return null;
		}
		charset = BooleanUtils.isBlank(charset) ? CommonConstant.UTF_8 : charset;
		try {
			return DigestUtils.md5Hex(value.getBytes(charset));
		}
		catch (UnsupportedEncodingException e) {
			return DigestUtils.md5Hex(value);
		}
	}
}
