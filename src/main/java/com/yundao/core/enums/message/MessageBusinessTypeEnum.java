package com.yundao.core.enums.message;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息业务类型枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum MessageBusinessTypeEnum {

	/**
	 * 产品详情
	 */
	VALUE_0(0),

	/**
	 * 路演详情
	 */
	VALUE_1(1),

	/**
	 * 资讯详情
	 */
	VALUE_2(2),

	/**
	 * 注册红包
	 */
	VALUE_3(3),

	/**
	 * 合格投资者认证红包
	 */
	VALUE_4(4),
	
	/**
	 * 产品公告
	 */
	VALUE_5(5);

	private Integer value;
	private static Map<Integer, MessageBusinessTypeEnum> enumMap = new HashMap<Integer, MessageBusinessTypeEnum>();

	static {
		EnumSet<MessageBusinessTypeEnum> set = EnumSet.allOf(MessageBusinessTypeEnum.class);
		for (MessageBusinessTypeEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private MessageBusinessTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	/**
	 * 获取消息业务类型
	 * 
	 * @param value
	 * @return
	 */
	public static MessageBusinessTypeEnum getMessageTargetTypeEnum(String value) {
		return enumMap.get(value);
	}

}