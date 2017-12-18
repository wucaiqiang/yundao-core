package com.yundao.core.enums.message;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息目标类型枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum MessageTargetTypeEnum {

	/**
	 * 设备
	 */
	PUSH_TOKEN("push_token"),

	/**
	 * 版本号
	 */
	VERSION("version");

	private String value;
	private static Map<String, MessageTargetTypeEnum> enumMap = new HashMap<String, MessageTargetTypeEnum>();

	static {
		EnumSet<MessageTargetTypeEnum> set = EnumSet.allOf(MessageTargetTypeEnum.class);
		for (MessageTargetTypeEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private MessageTargetTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 获取消息目标类型
	 * 
	 * @param value
	 * @return
	 */
	public static MessageTargetTypeEnum getMessageTargetTypeEnum(String value) {
		return enumMap.get(value);
	}

}