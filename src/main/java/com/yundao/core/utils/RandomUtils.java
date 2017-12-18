package com.yundao.core.utils;

import java.util.Random;

import com.yundao.core.constant.CommonConstant;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 生成随机字符
 * 
 * @author zhangmingxing
 *
 */
public class RandomUtils {

	/**
	 * 生成字母和数字组合字符串
	 * @return
	 */
	public static String getCharAndNumr() {
		String val = "";
		Random random = new Random();
		String length = ConfigUtils.getValue(CommonConstant.SMS_PWD_DIGIT);
		for (int i = 0; i < NumberUtils.toInt(length, 6); i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)){// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			}
			else if ("num".equalsIgnoreCase(charOrNum)){ // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
