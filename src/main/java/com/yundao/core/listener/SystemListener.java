package com.yundao.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.StaticValue;
import com.yundao.core.mq.MQLoadSystemListener;
import com.yundao.core.utils.HttpUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.qos.logback.ext.spring.web.WebLogbackConfigurer;

/**
 * 系统监听类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SystemListener implements ServletContextListener {

	private static WebApplicationContext applicationContext;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(application);

		StaticValue.realPath = application.getRealPath("");
		StaticValue.contextPath = application.getContextPath();

		application.setAttribute(CommonConstant.CONTEXT_PATH, StaticValue.contextPath);
		this.subclassInit();

		// 加载当前系统MQ订阅者
		MQLoadSystemListener.loadListener();
		WebLogbackConfigurer.initLogging(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HttpUtils.shutdown();
		WebLogbackConfigurer.shutdownLogging(event.getServletContext());
	}

	/**
	 * 子类可继承实现
	 */
	protected void subclassInit() {

	}

	/**
	 * 获取spring上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取bean
	 * 
	 * @param <T>
	 * @param beanName
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return applicationContext.getBean(beanName, requiredType);
	}

}
