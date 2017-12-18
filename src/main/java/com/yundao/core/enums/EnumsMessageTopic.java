/**
 * 
 */
package com.yundao.core.enums;

/**
 * @author Jon Chiang
 * @date 2016年8月29日
 */
public enum EnumsMessageTopic {
	FEED_MESSAGE_TOPIC("zcm_mq_test");

	String topic;

	EnumsMessageTopic(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}
 

}
