package com.yundao.core.cache.spring;

import com.yundao.core.cache.redis.JedisUtils;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * spring缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringCache implements Cache {

	private static Log log = LogFactory.getLog(SpringCache.class);

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		try {
			return JedisUtils.getJedis();
		}
		catch (Exception e) {
			log.error("获取缓存对象时异常", e);
			return null;
		}
	}

	@Override
	public ValueWrapper get(Object key) {
		try {
			Object result = JedisUtils.getObject((String) key);
			return result != null ? new SimpleValueWrapper(result) : null;
		}
		catch (Exception e) {
			log.error("获取缓存时异常", e);
			return null;
		}
	}

	@Override
	public void put(Object key, Object value) {
		try {
			JedisUtils.setObject((String) key, value);
		}
		catch (Exception e) {
			log.error("设置缓存时异常", e);
		}
	}

	@Override
	public void evict(Object key) {
		try {
			JedisUtils.remove((String) key);
		}
		catch (Exception e) {
			log.error("删除缓存时异常", e);
		}
	}

	@Override
	public void clear() {
		try {
			JedisUtils.clear();
		}
		catch (Exception e) {
			log.error("清空缓存时异常", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		try {
			Object result = JedisUtils.getObject((String) key);
			return (T) result;
		}
		catch (Exception e) {
			log.error("获取缓存时异常", e);
			return null;
		}
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		try {
			Object existValue = JedisUtils.getObject((String) key);
			if (existValue == null) {
				JedisUtils.setObject((String) key, value);
			}
			return existValue != null ? new SimpleValueWrapper(existValue) : null;
		}
		catch (Exception e) {
			log.error("设置缓存时异常", e);
			return null;
		}
	}

}