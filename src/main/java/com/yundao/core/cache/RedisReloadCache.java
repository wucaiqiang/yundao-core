package com.yundao.core.cache;

import com.yundao.core.cache.redis.JedisEnum;
import com.yundao.core.cache.visitor.Visitable;

/**
 * redis初始化时自动加载的缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public interface RedisReloadCache<V> extends ReloadCache<String, V>, Visitable {

	/**
	 * 缓存指定的键值并设置过期时间，若键key已在缓存中则返回其旧值
	 * 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public V put(String key, V value, long expireSeconds);

	/**
	 * 缓存指定的键值并设置过期时间，若键key已在缓存中则返回其旧值
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 * @param expx
	 * @param time
	 * @return
	 */
	public V put(String key, V value, JedisEnum.NXXX nxxx, JedisEnum.EXPX expx, long time);

	/**
	 * 增加指定的值
	 * 
	 * @param key
	 * @param value
	 */
	public void incrementBy(String key, int value);

	/**
	 * 获取增加的值
	 * 
	 * @param key
	 * @return
	 */
	public int getIncrementBy(String key);
}
