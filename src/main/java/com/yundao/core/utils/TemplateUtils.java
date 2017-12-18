/**
 * 
 */
package com.yundao.core.utils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模版工具类
 * 
 * @author Jon Chiang
 * @date 2016年8月22日
 */
public class TemplateUtils {
	private static Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
	private static final VelocityEngine velocityEngine;
	static {
		velocityEngine = new VelocityEngine();// 初始化velocity 引擎
		InputStream inputStream = TemplateUtils.class.getResourceAsStream("/velocity.properties");
		if (inputStream != null) {
			Properties properties = new Properties();
			try {
				properties.load(inputStream);
				velocityEngine.init(properties);
			} catch (Throwable e) {
				logger.error("初始化velocity失败", e);
			}
		} else {
			velocityEngine.init();
		}

	}

	/**
	 * 解析模版参数
	 * 
	 * @param content
	 * @param params
	 * @return
	 */
	public static String parse(String content, Map<String, Object> params) {
		VelocityContext context = new VelocityContext(params);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.evaluate(context, stringWriter, "feedTemplate", content);
		return stringWriter.toString();
	}
}
