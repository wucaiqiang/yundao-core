package com.yundao.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum NumberEnum {

	ZERO(0),

	ONE(1),
	
	TWO(2);

	private int value;
	private static Map<Integer, NumberEnum> enumMap = new HashMap<Integer, NumberEnum>();

	static {
		EnumSet<NumberEnum> set = EnumSet.allOf(NumberEnum.class);
		for (NumberEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private NumberEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	/**
	 * 获取数字枚举
	 * 
	 * @param value
	 * @return
	 */
	public static NumberEnum getNumberEnum(Integer value) {
		return enumMap.get(value);
	}

}