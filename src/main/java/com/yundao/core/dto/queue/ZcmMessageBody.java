/**
 * 
 */
package com.yundao.core.dto.queue;

/**
 * @author Jon Chiang
 * @date 2016年8月22日
 */
public class ZcmMessageBody {
	// 流水号
	String seqNo;

	// 消息正文
	String body;

	// 消息处理类名称
	String handler;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

}
