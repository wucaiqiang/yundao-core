package com.yundao.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存redis key值
 * 
 * @author zhangmingxing
 *
 */
public enum RedisEnum {
	
	/**
	 * 角色id 层级关系
	 */
	UBS_ROLE_IDS("ubs_role_ids_"),

	/**
	 * 角色id 无层级关系
	 */
	UBS_ROLE_ID("ubs_role_id_"),
	
	/**
	 * 用户登录随机数
	 */
	UBS_LOGIN_RANDOM_USERNAME_MOBILE("ubs_login_random_username_mobile_"),
	
	/**
	 * 图片验证码
	 */
	CAPTCHA_UUID("captcha_uuid_");
	
	private String value;
	private static Map<String, RedisEnum> enumMap = new HashMap<String, RedisEnum>();

	static {
		EnumSet<RedisEnum> set = EnumSet.allOf(RedisEnum.class);
		for (RedisEnum each : set) {
			enumMap.put(each.getValue(), each);
		}
	}

	private RedisEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 获取枚举
	 * 
	 * @param value
	 * @return
	 */
	public static RedisEnum getStringEnum(String value) {
		return enumMap.get(value);
	}

}