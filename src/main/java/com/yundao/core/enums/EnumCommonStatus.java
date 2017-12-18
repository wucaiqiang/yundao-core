package com.yundao.core.enums;

/**
 * 通用状态
 * @author Jon Chiang
 * @date 2016年9月20日
 */
public enum EnumCommonStatus {

	/**
	 * 默认
	 */
	NORMAL((byte)0),

	/**
	 * 有效
	 */
	EFFECTIVE((byte)1),

	/**
	 * 无效
	 * 
	 */
	DISABLED((byte)2);

	 
	private byte value;

	private EnumCommonStatus(byte value) {
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}
	
}
