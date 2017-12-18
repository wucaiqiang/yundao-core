/**
 * 
 */
package com.yundao.core.dto.queue;

import com.yundao.core.code.Result;

/**
 * 操作类的父类
 * @author Jon Chiang
 * @date 2016年8月22日
 */
public interface IMessageHandler {

	/**
	 * 消费消息具体执行类接口
	 * @param body body是最底层的body
	 * @return
	 */
	Result<?> exec(String seqNo,String body);
}
