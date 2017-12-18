package com.yundao.core.common;

import java.util.HashMap;
import java.util.Map;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.filter.RequestCommonParams;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.HttpUtils;
import com.yundao.core.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;

import com.yundao.core.utils.ConfigUtils;

/**
 * 图片验证码等 保存  查询  类
 * @author zhangmingxing
 *
 */
public class Common {
	
	private static Log log = LogFactory.getLog(Common.class);

	/**
	 * 查询
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static String getValueByName(String name) throws Exception{
		Map<String, String> methodParams = new HashMap<String, String>();
		methodParams.put("name", name);
		String url = ConfigUtils.getValue("ubs.common.select.url");
		Map<String, Object> result = doSend(url, CommonConstant.GSTMETHOD, methodParams);
		log.info("result==="+result);
		if (BooleanUtils.isEmpty(result)) {
			String err = "返回的result为空!";
			log.error(err);
			return null;
		}
		Map<String, Object> map = (Map<String, Object>) result.get(CommonConstant.RESULT);
		if (BooleanUtils.isEmpty(map)) {
			String err = "返回的值为空";
			log.error(err);
			return null;
		}
		String value = (String) map.get("value");
		return value;
		
	}
	
	/**
	 * 增加
	 * @param map
	 * @throws Exception
	 */
	public static void addValue(Map<String,String> map) throws Exception{
		RequestCommonParams requestParams = RequestCommonParams.newDefault(map);
		String url = ConfigUtils.getValue("ubs.common.insert.url");
		HttpUtils.post(url, requestParams);
	}
	
	/**
	 * 删除
	 * @param map
	 * @throws Exception
	 */
	public static void deleteValue(String name) throws Exception{
		Map<String, String> methodParams = new HashMap<String, String>();
		methodParams.put("name", name);
		RequestCommonParams requestParams = RequestCommonParams.newDefault(methodParams);
		String url = ConfigUtils.getValue("ubs.common.delete.url");
		HttpUtils.post(url, requestParams);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> doSend(String url, String method, Map<String, String> params) throws Exception {
		String response = null;
		RequestCommonParams requestParams = RequestCommonParams.newDefault(params);
		if (StringUtils.equals(method, CommonConstant.GSTMETHOD)) {
			response = HttpUtils.get(url, requestParams);
		}
		else if (StringUtils.equals(method, CommonConstant.POSTMETHOD)) {
			response = HttpUtils.post(url, requestParams);
		}
		else {
			String errInfo = "请求方法异常:" + method;
			log.error(errInfo);
			throw new Exception(errInfo);
		}

		if (StringUtils.isEmpty(response)) {
			log.warn("请求返回为空!");
			return null;
		}
		log.info("收到请求回复: " + response);
		return JsonUtils.jsonToObject(response, Map.class);
	}
}
