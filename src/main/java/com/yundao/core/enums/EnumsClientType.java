/**
 * 
 */
package com.yundao.core.enums;

/**
 * 用户客户端类型，用户ubs登录等
 * @author Jon Chiang
 * @date 2016年9月7日
 */
public enum EnumsClientType {
	ZCMALL_APP("zcmall.app"), //
	HTML5("html5"), //
	CRM("crm"), //
	H5_PD("h5.pd"), //
	WEB_PD("web.pd"), //
	FRONT_ZCMALL("front.zcmall");

	private String type;

	EnumsClientType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
