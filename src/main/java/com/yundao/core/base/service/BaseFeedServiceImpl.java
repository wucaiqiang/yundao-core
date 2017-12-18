/**
 * 
 */
package com.yundao.core.base.service;

import com.yundao.core.base.model.FeedModel;
import com.yundao.core.constant.MessageHandlerConsant;
import com.yundao.core.dto.queue.ZcmMessageBody;
import com.yundao.core.utils.SeqNoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.yundao.core.code.Result;
import com.yundao.core.code.config.CommonCode;
import com.yundao.core.utils.ConfigUtils;

/**
 * @author Jon Chiang
 * @date 2016年8月24日
 */
public class BaseFeedServiceImpl  {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired(required=false)
	ProducerBean producerBean;

	public Result<Integer> sendMessage(FeedModel feed) {
		if (null == feed) {
			return Result.newFailureResult(CommonCode.COMMON_1000);
		}
		ZcmMessageBody body = new ZcmMessageBody();
		body.setSeqNo(SeqNoGenerator.generateBizUID("FD"));
		body.setHandler(MessageHandlerConsant.FEED_MESSAGE_HANDLER);
		body.setBody(JSONObject.toJSONString(feed));
		Message message = new Message(ConfigUtils.getValue("feed.mq.topic"), ConfigUtils.getValue("feed.mq.topic"), JSONObject.toJSONString(body).getBytes());
		try {
			SendResult sendResult = producerBean.send(message);
			if(null != sendResult){
				logger.info("发送动态成功 messageId:"+sendResult.getMessageId());
			}
		} catch (Exception e) {
			logger.error("发送feed消息失败了" + body.getBody(), e);
			return Result.newFailureResult();
		}
		return Result.newSuccessResult();
	}

}
