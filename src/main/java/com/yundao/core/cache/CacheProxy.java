package com.yundao.core.cache;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 缓存代理
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public final class CacheProxy<K, V> implements Cache<K, V> {

	private String name;
	private Cache<K, V> cache = null;

	public void setCache(Cache<K, V> cache) {
		this.cache = cache;
	}
	
	public Cache<K, V> getCache() {
		return this.cache;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean containsKey(K key) {
		return cache == null ? false : cache.containsKey(key);
	}

	@Override
	public V get(K key) {
		return cache == null ? null : cache.get(key);
	}

	@Override
	public long getHitCount() {
		return cache == null ? 0 : cache.getHitCount();
	}

	@Override
	public long getQueryCount() {
		return cache == null ? 0 : cache.getQueryCount();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return cache == null ? null : cache.iterator();
	}

	@Override
	public Set<K> keySet() {
		return cache == null ? null : cache.keySet();
	}

	@Override
	public V put(K key, V value) {
		return cache == null ? null : cache.put(key, value);
	}

	@Override
	public void clear() {
		if (cache != null) {
			cache.clear();
		}
	}

	@Override
	public V remove(K key) {
		return cache == null ? null : cache.remove(key);
	}

	@Override
	public long size() {
		return cache == null ? 0 : cache.size();
	}
}
