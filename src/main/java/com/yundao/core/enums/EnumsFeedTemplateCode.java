/**
 * 
 */
package com.yundao.core.enums;

/**
 * 模版code
 * 
 * @author Jon Chiang
 * @date 2016年8月29日
 */
public enum EnumsFeedTemplateCode {
	REGISTED("REGISTED"), // 2016-6-30 18:30 客户通过 注册1
	LOGINED("LOGINED"), // 2016-7-3 14:15 客户通过登陆了2
	CERTIFICATION("CERTIFICATION "), // 2016-7-15 14:15 客户完成了合格投资者认证3
	JOIN_LIVE("JOIN_LIVE"), // 2016-7-19 14:15 客户参加了直播：财神主播的《用6年打造了招财猫的100亿市值》4
	VIEW_PRODUCT_APP("VIEW_PRODUCT_APP"), // 2016-7-24 14:15 客户在APP查看了产品：招财猫现金宝5
	APPPOINTMENT_APP("APPPOINTMENT_APP"), // 2016-8-4 14:15 客户在APP预约了产品：招财猫现金宝6
	BUY_PRODUCT_APP("BUY_PRODUCT_APP"), // 2016-8-9 14:15 客户购买了产品：招财猫现金宝7
	FOLLOW_UP("FOLLOW_UP"), // 2016-8-4 14:15 冯丹创建了跟进记录：这个用户好热心，给他推荐了招财猫现金宝8
	REVISIT_RECORD("REVISIT_RECORD"), // 2016-8-714:15廖文君创建了回访记录：对服务不满意，不喜欢理财师推荐的产品9
	AFTER_SALES_SERVICE("AFTER_SALES_SERVICE"), // 2016-8-1017:15艾金波完成了售后服务：发送短信通知：{content}10
	CHANGE_FINANICAL_PLANNER("CHANGE_FINANICAL_PLANNER"); // 运营总监${realName}于${createTime}将客户的理财师从${oldMgrName}调换到${newMgrName}，调换理由是:${remark}11
	
	String code;

	EnumsFeedTemplateCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
