package com.yundao.core.config.common;

/**
 * 常用配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public enum CommonConfigEnum {

	/**
	 * 若系统没有配置异步请求的容量时，则用此默认容量
	 */
	SYSTEM_CAPACITY("system.capacity"),

	/**
	 * 若系统没有配置异步请求的丢弃请求的间隔时，则用此值，单位毫秒
	 */
	SYSTEM_ABANDON_INTERVAL("system.abandon.interval"),

	/**
	 * 安全授权的链接（校验当前资源）
	 */
	SECURITY_AUTHORIZE_URL("security.authorize.url"),
	
	/**
	 * 安全授权的链接（校验子资源）
	 */
	SECURITY_AUTHORIZE_CHILDREN_URL("security.authorize.children.url"),

	/**
	 * cookie的md5盐值
	 */
	COOKIE_MD5_SIGN("cookie.md5.sign"),
	
	/**
	 * 登录后的有效时间
	 */
	LOGIN_VALID_SECONDS("login.valid.seconds"),
	
	/**
	 * 重新加载缓存的目录
	 */
	RELOAD_CACHE_DIR("reload.cache.dir"),
	
	/**
	 * 重新加载环境的目录
	 */
	RELOAD_ENVIRONMENT_DIR("reload.environment.dir");

	private String key;

	private CommonConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
