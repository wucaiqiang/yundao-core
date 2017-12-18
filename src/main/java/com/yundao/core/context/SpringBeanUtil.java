/**
 * 
 */
package com.yundao.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * @author Jiang Liang
 * @date 2016年9月30日下午3:36:48
 */
public class SpringBeanUtil {
	private static ApplicationContext applicationContext;

	public static void init() {
		applicationContext = ContextLoader.getCurrentWebApplicationContext();
	}

	static {
		init();
	}

	/**
	 * 获取spring托管的对象
	 * 
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 根据类型和名称获取
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}
}
