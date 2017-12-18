package com.yundao.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * bigdecimal工具类
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class BigDecimalUtils {

	private final static int SCALE_NUM = 2;

	/**
	 * 格式化，取两位小数，>5进，否则舍弃
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal upFormat(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.setScale(SCALE_NUM, RoundingMode.HALF_UP);
	}

	/**
	 * 格式化，取两位小数后直接去掉其它小数位
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal downFormat(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.setScale(SCALE_NUM, RoundingMode.DOWN);
	}

}