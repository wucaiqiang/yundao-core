package com.yundao.core.threadlocal.config;

/**
 * 本地线程配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public enum ThreadLocalConfigEnum {

	/**
	 * 是否需要校验请求的时间，0：否 1：是
	 */
	IS_VALIDATE_REQUEST_TIME("is.validate.request.time"),

	/**
	 * 请求的过期时间，单位秒
	 */
	REQUEST_TIME_EXPIRE("request.time.expire"),
	
	/**
	 * 请求的加密类型，若多个以逗号分隔
	 */
	REQUEST_SIGN_TYPE("request.sign.type");

	private String key;

	private ThreadLocalConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
