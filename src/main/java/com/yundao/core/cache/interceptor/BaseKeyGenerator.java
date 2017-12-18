package com.yundao.core.cache.interceptor;

import java.lang.reflect.Method;

import com.yundao.core.constant.CommonConstant;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import com.yundao.core.utils.ConfigUtils;

/**
 * 键生成器
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class BaseKeyGenerator extends SimpleKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return getKey(method.getDeclaringClass(), params);
	}

	/**
	 * 获取键值
	 * 	
	 * @param clazz
	 * @param params
	 * @return
	 */
	public static String getKey(Class<?> clazz, Object... params) {
		String className = clazz.getName();
		return getKey(className, params);
	}

	/**
	 * 获取键值
	 * 
	 * @param className
	 * @param params
	 * @return
	 */
	public static String getKey(String className, Object... params) {
		String systemId = ConfigUtils.getValue(CommonConstant.ID);
		Object param = generateKey(params);
		return systemId + ":" + className + "[" + param + "]";
	}

}