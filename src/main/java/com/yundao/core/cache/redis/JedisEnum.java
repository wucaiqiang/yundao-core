package com.yundao.core.cache.redis;

/**
 * jedis枚举
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public enum JedisEnum {

	JedisEnum;

	/**
	 * 值为： NX或XX； NX：仅当key不存在时设置，XX：仅当key存在时设置
	 *
	 */
	public enum NXXX {

		NX("NX"),

		XX("XX");

		private String value;

		private NXXX(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 
	 * 值为：EX或PX；超时时间单位: EX为秒，PX为毫秒
	 *
	 */
	public enum EXPX {

		EX("EX"),

		PX("PX");

		private String value;

		private EXPX(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
