package com.yundao.core.mq;

import com.yundao.core.log.Log;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.ConfigUtils;

/**
 * 加载系统的监听器
 * @author gjl
 *
 */
public class MQLoadSystemListener {
	
	private static Log log = LogFactory.getLog(MQLoadSystemListener.class);
	
	private static String SPLIT_TYPE = ";";
	private static String SPLIT_STR = ",";
	
	public static void loadListener(){
		try{
			String systemCode = ConfigUtils.getValue(CommonConstant.ID);
			if(BooleanUtils.isEmpty(systemCode)){
				return;
			}
			String listenerConf = MQFileConfig.getValue(MQConfigEnum.LISTENER);
			//获取MQ订阅者配置
			if(BooleanUtils.isEmpty(listenerConf)){
				return;
			}
			//开启加载MQ订阅者
			String[] listenerArr = listenerConf.split(SPLIT_TYPE);
			for(String listener : listenerArr){
				String[] values = listener.split(SPLIT_STR);
				if(!validate(values)){
					log.info("配置MQ监听器验证参数出错：" + values);
					continue;
				}
				//获取回调类
				String className = values[2];
				Object obj = (Object) Class.forName(className).newInstance();
				if(!(obj instanceof MQCallBack)){
					log.info("配置回调类没有实现MQCallBack接口");
					continue;
				}
				//启动MQ监听器
				MQConsumerListener.MQListener(values[0], values[1], (MQCallBack) obj, values[3]);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 验证配置文件
	 * @param values
	 * @return
	 */
	private static boolean validate(String[] values) {
		if(values.length != 4)
			return false;
		for(String value : values){
			if(BooleanUtils.isEmpty(value)){
				return false;
			}
		}
		return true;
	}
}
