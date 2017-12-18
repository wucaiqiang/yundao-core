/**
 * 
 */
package com.yundao.core.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jiang Liang
 * @date 2016年11月15日下午2:04:56
 */
public class PasswordUtils {
	public enum LEVEL {
		EASY, MIDIUM, STRONG, VERY_STRONG, EXTREMELY_STRONG
	}

	public static LEVEL getPasswordLevel(String passwd) {
		if (passwd.length() < 8) {
			return LEVEL.EASY;
		}
		int level = 0;
		if (passwd.matches(".*[0-9]+.*")) {
			level += 20;
		}
		if (passwd.replaceAll("[\\u4e00-\\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() > 0) {// 特殊字符
			level += 20;
		}
		if (passwd.matches(".*[a-zA-Z]+.*")) {
			level += 20;
		}
		System.out.println(passwd + " level " + level);
		if (level <= 20) {
			return LEVEL.EASY;
		} else if (level <= 40) {
			return LEVEL.MIDIUM;
		} else if (level <= 60) {
			return LEVEL.STRONG;
		} else if (level <= 80) {
			return LEVEL.VERY_STRONG;
		} else {
			return LEVEL.EXTREMELY_STRONG;
		}

	}

	public static void main(String[] args) {
		getPasswordLevel("12345678");
		getPasswordLevel("asdf1234fa");
		getPasswordLevel("wucaiqiang");
		getPasswordLevel("wucaiqiang1");
		getPasswordLevel("Wucaiqiang1!");
		getPasswordLevel("12345678");
		getPasswordLevel("12345678a");
		getPasswordLevel("fuck!123");
		getPasswordLevel("2016fuckyou!");
		getPasswordLevel("fuck!123");
	}
}
