/**
 * 
 */
package com.yundao.core.context;

import com.yundao.core.utils.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.yundao.core.code.Result;
import com.yundao.core.dto.queue.IMessageHandler;
import com.yundao.core.dto.queue.ZcmMessageBody;

/**
 * 通用消息队列监听监听
 * 
 * @author Jon Chiang
 * @date 2016年8月19日
 */
public class ZcmMessageListener implements MessageListener {


	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static boolean isFirstRun = true;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		logger.info("Receive: " + message.getMsgID());
		if (isFirstRun) {
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			isFirstRun = false;
		}
		try {
			String queueBody = new String(message.getBody());
			if (BooleanUtils.isBlank(queueBody)) {
				logger.warn("收到的数据为空 msgID:" + message.getMsgID() + ",key:" + message.getKey());
				return Action.ReconsumeLater;
			}
			
			logger.info("收到消息" + queueBody);
			ZcmMessageBody zcmMessageBody = JSONObject.parseObject(queueBody, ZcmMessageBody.class);
			IMessageHandler messageHandler = SpringBeanUtil.getBean(zcmMessageBody.getHandler(), IMessageHandler.class);
			Result<?> result = messageHandler.exec(zcmMessageBody.getSeqNo(), zcmMessageBody.getBody());
			if (result.hasFail()) {
				logger.warn("seq" + zcmMessageBody.getSeqNo() + ",code:" + result.getCode());
				logger.warn("消费消息失败" + messageHandler.getClass().getName() + "seqNo" + zcmMessageBody.getSeqNo() + ",body" + queueBody + "code;");
				return Action.ReconsumeLater;
			}
			// FIXME 按目前的情况看，失败的会一直堆在队列里面，然，又不可随意删除，故此处需要标记。后期需改之。
			return Action.CommitMessage;
		} catch (Exception e) {
			// 消费失败
			logger.error("消费失败", e);
			return Action.ReconsumeLater;
		}
	}

}
