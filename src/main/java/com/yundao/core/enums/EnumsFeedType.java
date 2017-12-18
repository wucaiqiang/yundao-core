/**
 * 
 */
package com.yundao.core.enums;

/**
 * 消息类型
 * @author Jon Chiang
 * @date 2016年8月29日
 */
public enum EnumsFeedType {
	LOGIN("LOGIN"),//2016-6-30 18:30 客户通过APP注册 1
	REGISTER("REGISTER"),//2016-7-3 14:15 客户通过登陆了APP2
	VIEW_PRODUCT("VIEW_PRODUCT"),//查看产品3
	CERTIFICATION ("CERTIFICATION"),//2016-7-15 14:15 客户完成了合格投资者认证4
	JOIN_LIVE("JOIN_LIVE"),//2016-7-19 14:15 客户参加了直播：财神主播的《用6年打造了招财猫的100亿市值》5
	APPPOINTMENT("APPPOINTMENT_APP"),//预约产品 6
	BUY_PRODUCT("BUY_PRODUCT_APP"),//购买了产品 7
	FOLLOW_UP("FOLLOW_UP"),//  创建了跟进记录  8
	REVISIT_RECORD("REVISIT_RECORD"),//撩文君创建了回访记录 9 
	CUSTOMER_SERVICE_NORGISTI("CUSTOMER_SERVICE_NORGISTI"),//客服操作 10
	AFTER_SALES_SERVICE("AFTER_SALES_SERVICE"),//售后服务 11
	DIST_NEW_CUSTOMER("DIST_NEW_CUSTOMER"),//分配了新的客户 12
	NOTICE_PRD_SETUP("NOTICE_PRD_SETUP"),//产品成立公告 13
	BATCH_DIST_CUSTOMER("batchDistCustomer");//批量分配客户 14
	
	String type;
	EnumsFeedType(String type){
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	
}
