package com.yundao.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * sql符号枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum SqlSymbolEnum {

	EQUALS("="),

	GT(">="),

	GREAT(">"),

	LT("<="),

	LESS("<"),

	NE("!=");

	private String value;
	private static Map<String, SqlSymbolEnum> enumMap = new HashMap<String, SqlSymbolEnum>();

	static {
		EnumSet<SqlSymbolEnum> set = EnumSet.allOf(SqlSymbolEnum.class);
		for (SqlSymbolEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private SqlSymbolEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 获取sql符号枚举
	 * 
	 * @param value
	 * @return
	 */
	public static SqlSymbolEnum getSqlSymbolEnum(String value) {
		SqlSymbolEnum result = enumMap.get(value);
		if (result == null) {
			result = EQUALS;
		}
		return result;
	}

}