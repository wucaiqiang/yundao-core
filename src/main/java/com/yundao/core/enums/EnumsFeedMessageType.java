/**
 * 
 */
package com.yundao.core.enums;

/**
 * 消息类型 大的类型，以客户分配、公告、客户动态为纬度
 * @author Jon Chiang
 * @date 2016年8月29日
 */
public enum EnumsFeedMessageType {
	CUSTOMER_DIST("CUSTOMER_DIST"),//消息
	CUSTOMER_FEED("CUSTOMER_FEED"),//客户动态/客户轨迹
	NOTICE("NOTICE");//公告
	String type;
	EnumsFeedMessageType(String type){
		this.type = type;
	}
	public String getType() {
		return type;
	} 
	
	
}
