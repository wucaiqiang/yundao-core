package com.yundao.core.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 缓存接口
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {

	/**
	 * 设置缓存名字
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 获取缓存的名字
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取指定键的值
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key);

	/**
	 * 是否包含指定的键
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key);

	/**
	 * 缓存指定的键值，若键key已在缓存中则返回其旧值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key, V value);

	/**
	 * 清空缓存中的内容
	 */
	public void clear();

	/**
	 * 删除指定的键，并返回当前的键值
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key);

	/**
	 * 获取缓存的大小
	 * 
	 * @return
	 */
	public long size();

	/**
	 * 获取缓存中所有的键
	 * 
	 * @return
	 */
	public Set<K> keySet();

	/**
	 * 获取查询缓存的次数
	 * 
	 * @return
	 */
	public long getQueryCount();

	/**
	 * 获取命中缓存的次数
	 * 
	 * @return
	 */
	public long getHitCount();

	/**
	 * 获取缓存的迭代器
	 * 
	 * @return
	 */
	public Iterator<Map.Entry<K, V>> iterator();
}
