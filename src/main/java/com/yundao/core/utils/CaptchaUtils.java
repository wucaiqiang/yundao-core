
package com.yundao.core.utils;

/**
 * Function: Reason: Date: 2017年9月13日 下午4:07:57
 * 
 * @author wucq
 * @version
 */
public class CaptchaUtils {
	public static Integer getCaptcha() {
		int captcha = (int) ((Math.random() * 9 + 1) * 100000);
		return captcha;
	}
}
