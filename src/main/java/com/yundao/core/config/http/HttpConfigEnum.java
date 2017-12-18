package com.yundao.core.config.http;

/**
 * http配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public enum HttpConfigEnum {

	/**
	 * solr连接
	 */
	SOLR_URL("solr.url"),

	/**
	 * 读取超时
	 */
	READ_TIMEOUT("read.timeout"),

	/**
	 * 连接超时
	 */
	CONNECTION_TIMEOUT("connection.timeout"),

	/**
	 * 每个服务的最大连接数
	 */
	MAX_CONNECTIONS_PER_HOST("max.connections.per.host"),

	/**
	 * 最大连接数
	 */
	MAX_TOTAL_CONNECTIONS("max.total.connections");

	private String key;

	private HttpConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
