package com.yundao.core.cache;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 缓存包装类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class CacheWrapper {

	private static Log log = LogFactory.getLog(CacheWrapper.class);

	private Cache cache;
	private String cacheName;
	private long removeInterval = -1;
	private long lastRemoveTime = System.currentTimeMillis();

	public CacheWrapper(Cache cache, String cacheName) {
		this.cache = cache;
		this.cacheName = cacheName;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public long getRemoveInterval() {
		return removeInterval;
	}

	public void setRemoveInterval(long removeInterval) {
		this.removeInterval = removeInterval;
	}

	public long getLastRemoveTime() {
		return lastRemoveTime;
	}

	public void setLastRemoveTime(long lastRemoveTime) {
		this.lastRemoveTime = lastRemoveTime;
	}

	/**
	 * 删除缓存中过期的或最近最少访问的数据
	 * 
	 * @return
	 */
	public boolean remove() {
		boolean result = false;
		long now = System.currentTimeMillis();
		long checkInterval = now - lastRemoveTime;

		if (removeInterval > 0 && checkInterval <= removeInterval) {
			removeInterval *= 1000;
			if (this.cache instanceof DefaultCache) {
				DefaultCache reload = (DefaultCache) cache;
				reload.remove(null);
				result = reload.getLastRemoveStatus();
			}
			if (result) {
				lastRemoveTime = now;
			}
		}
		log.info("删除缓存中数据, result=" + result + ", cacheName=" + this.cacheName + ", now=" + now + ", checkInterval="
				+ checkInterval + ", removeInterval=" + removeInterval);
		return result;
	}
}
