package com.yundao.core.cache.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.yundao.core.log.Log;
import org.apache.commons.lang.math.NumberUtils;

import com.yundao.core.cache.RedisReloadCache;
import com.yundao.core.cache.redis.JedisEnum.EXPX;
import com.yundao.core.cache.redis.JedisEnum.NXXX;
import com.yundao.core.cache.visitor.Visitor;
import com.yundao.core.log.LogFactory;

/**
 * redis实现可加载缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class JedisReloadCache<V> implements RedisReloadCache<V> {

	private static Log log = LogFactory.getLog(JedisReloadCache.class);

	private String name;
	private AtomicLong queryCount = new AtomicLong(0);
	private AtomicLong hitCount = new AtomicLong(0);

	private volatile long lastReloadTime = 0;
	private volatile long lastReloadConsumeTime = 0;
	private volatile boolean lastReloadStatus = false;

	public JedisReloadCache() {
		this(null);
	}

	public JedisReloadCache(Map<String, Object> map) {
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
	public V get(String key) {
		this.queryCount.incrementAndGet();
		key = this.getKey(key);
		V value = JedisUtils.getObject(key);
		if (value != null) {
			this.hitCount.incrementAndGet();
		}
		return value;
	}

	@Override
	public boolean containsKey(String key) {
		key = this.getKey(key);
		return JedisUtils.containsKey(key);
	}

	@Override
	public V put(String key, V value) {
		V result = null;
		key = this.getKey(key);
		if (JedisUtils.containsKey(key)) {
			result = JedisUtils.getObject(key);
		}
		JedisUtils.setObject(key, value);
		return result;
	}

	@Override
	public V put(String key, V value, long expireSeconds) {
		String redisKey = this.getKey(key);
		NXXX nxxx = NXXX.NX;
		if (JedisUtils.containsKey(redisKey)) {
			nxxx = NXXX.XX;
		}
		return put(key, value, nxxx, EXPX.EX, expireSeconds);
	}

	@Override
	public V put(String key, V value, NXXX nxxx, EXPX expx, long time) {
		V result = null;
		key = this.getKey(key);
		if (JedisUtils.containsKey(key)) {
			result = JedisUtils.getObject(key);
		}
		JedisUtils.setObject(key, value, nxxx, expx, time);
		return result;
	}

	@Override
	public void incrementBy(String key, int value) {
		key = this.getKey(key);
		JedisUtils.incrementBy(key, value);
	}

	@Override
	public int getIncrementBy(String key) {
		key = this.getKey(key);
		return NumberUtils.toInt(JedisUtils.getString(key));
	}

	@Override
	public void clear() {
		Set<String> keySet = this.keySet();
		int size = keySet.size();
		if (size > 0) {
			String[] keyArray = new String[size];
			int index = 0;
			for (String key : keySet) {
				keyArray[index] = key;
				index++;
			}
			JedisUtils.remove(keyArray);
		}
	}

	@Override
	public V remove(String key) {
		V result = null;
		key = this.getKey(key);
		if (JedisUtils.containsKey(key)) {
			result = JedisUtils.getObject(key);
			JedisUtils.remove(key);
		}
		return result;
	}

	@Override
	public long size() {
		Set<String> keys = this.keySet();
		return keys.size();
	}

	@Override
	public Set<String> keySet() {
		return JedisUtils.keySet(this.getKey("*"));
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
	public Iterator<Entry<String, V>> iterator() {
		return null;
	}

	@Override
	public void reload() {
		log.info("开始加载缓存");
		this.queryCount.set(0);
		this.hitCount.set(0);

		lastReloadTime = System.currentTimeMillis();
		Map<String, V> reloadMap = this.doReload();
		long reloadEnd = System.currentTimeMillis();
		lastReloadConsumeTime = reloadEnd - lastReloadTime;
		log.info("已加载子类缓存, lastReloadTime=" + lastReloadTime + ", lastReloadConsumeTime=" + lastReloadConsumeTime);

		if (reloadMap != null) {
			Map<String, V> batchMap = new HashMap<String, V>(reloadMap.size());
			for (Map.Entry<String, V> entry : reloadMap.entrySet()) {
				batchMap.put(this.getKey(entry.getKey()), entry.getValue());
			}
			JedisUtils.batchSetObject(batchMap);
			lastReloadStatus = true;
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
	public abstract Map<String, V> doReload();

	/**
	 * 获取保存进redis的key
	 * 
	 * @param key
	 * @return
	 */
	public String getKey(String key) {
		return this.name + ":" + key;
	}
}
