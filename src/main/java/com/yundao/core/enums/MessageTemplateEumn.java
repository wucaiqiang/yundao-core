/**
 * 
 */
package com.yundao.core.enums;

/**
 * @author Jon Chiang
 * @date 2016年7月19日
 */
public enum MessageTemplateEumn {
	REGISTER_ZCMALL_TEMPLATE("register_zcmall"), FIND_PWD_ZCMALL_TEMPLATE("findPwd_zcmall"), PRE_ORDER_ZCMALL_TEMPLATE("zcmall_front_pre"),RETRIEVE_PWD_ZCMALL_TEMPLATGE("retrieve_pwd_zcmall") ;

	private String key;

	private MessageTemplateEumn(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
