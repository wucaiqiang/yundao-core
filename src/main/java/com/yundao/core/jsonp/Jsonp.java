package com.yundao.core.jsonp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yundao.core.constant.CommonConstant;

/**
 * Jsonp注解
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Jsonp {

	/**
	 * 回调函数的参数名
	 * 
	 * @return
	 */
	String name() default CommonConstant.CALLBACK;
}