package com.yundao.core.enums.mechanism;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 机构类型枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum MechanismTypeEnum {

	/**
	 * 直销
	 */
	VALUE_0(0, "ZC"),

	/**
	 * 子公司
	 */
	VALUE_1(1, "ZZ"),

	/**
	 * 合资公司
	 */
	VALUE_2(2, "HZ"),

	/**
	 * 加盟商
	 */
	VALUE_3(3, "JM"),

	/**
	 * 供应商
	 */
	VALUE_4(4, "GY"),

	/**
	 * 代理商
	 */
	VALUE_5(5, "DL");

	private int value;
	private String code;
	private static Map<Integer, MechanismTypeEnum> enumMap = new HashMap<Integer, MechanismTypeEnum>();

	static {
		EnumSet<MechanismTypeEnum> set = EnumSet.allOf(MechanismTypeEnum.class);
		for (MechanismTypeEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private MechanismTypeEnum(int value, String code) {
		this.value = value;
		this.code = code;
	}

	public int getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 获取机构类型枚举
	 * 
	 * @param value
	 * @return
	 */
	public static MechanismTypeEnum getMechanismTypeEnum(Integer value) {
		return enumMap.get(value);
	}

}