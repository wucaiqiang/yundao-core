package com.yundao.core.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.Cacheable;

/**
 * 填加缓存注解，若缓存中存在，则不执行
 * 
 * @author wupengfei wupf86@126.com
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(value = "cache")
public @interface CacheAddIfAbsent {
	
}