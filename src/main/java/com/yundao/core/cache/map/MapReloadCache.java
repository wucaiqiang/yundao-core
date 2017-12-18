package com.yundao.core.cache.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Maps;
import com.yundao.core.cache.ReloadCache;
import com.yundao.core.cache.visitor.Visitor;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * map实现可加载缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public abstract class MapReloadCache<K, V> implements ReloadCache<K, V> {

	private static Log log = LogFactory.getLog(MapReloadCache.class);

	private String name;
	private Map<K, V> cache = Maps.newConcurrentMap();

	private AtomicLong queryCount = new AtomicLong(0);
	private AtomicLong hitCount = new AtomicLong(0);

	private volatile long lastReloadTime = 0;
	private volatile long lastReloadConsumeTime = 0;
	private volatile boolean lastReloadStatus = false;

	public MapReloadCache() {
		this(null);
	}

	public MapReloadCache(Map<String, Object> map) {
		init(map);
	}

	private void init(Map<String, Object> map) {

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
	public V get(K key) {
		this.queryCount.incrementAndGet();
		V value = cache.get(key);
		if (value != null) {
			this.hitCount.incrementAndGet();
		}
		return value;
	}

	@Override
	public boolean containsKey(K key) {
		return cache.containsKey(key);
	}

	@Override
	public V put(K key, V value) {
		return cache.put(key, value);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public V remove(K key) {
		return cache.remove(key);
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public Set<K> keySet() {
		return cache.keySet();
	}

	@Override
	public long getQueryCount() {
		return this.queryCount.get();
	}

	@Override
	public long getHitCount() {
		return this.hitCount.get();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return cache.entrySet().iterator();
	}

	@Override
	public void reload() {
		log.info("开始加载缓存");
		this.queryCount.set(0);
		this.hitCount.set(0);

		lastReloadTime = System.currentTimeMillis();
		Map<K, V> reloadMap = this.doReload();
		long reloadEnd = System.currentTimeMillis();
		lastReloadConsumeTime = reloadEnd - lastReloadTime;
		log.info("已加载子类缓存, lastReloadTime=" + lastReloadTime + ", lastReloadConsumeTime=" + lastReloadConsumeTime);

		if (reloadMap != null) {
			lastReloadStatus = true;
			cache = reloadMap;
			log.info("成功加载缓存");
		}
	}

	@Override
	public long getLastReloadTime() {
		return lastReloadTime;
	}

	@Override
	public long getLastReloadConsumeTime() {
		return lastReloadConsumeTime;
	}

	@Override
	public boolean getLastReloadStatus() {
		return lastReloadStatus;
	}

	@Override
	public void accept(Visitor vistor, String name) {
		vistor.visitReloadCache(this, name);
	}

	/**
	 * 子类实现提供待缓存的内容，若返回null则不加载
	 * 
	 * @return
	 */
	public abstract Map<K, V> doReload();

}
