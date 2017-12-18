package com.yundao.core.cache.monitor;

import com.yundao.core.cache.Cache;
import com.yundao.core.cache.CacheFactory;
import com.yundao.core.cache.visitor.LogVisitor;
import com.yundao.core.cache.visitor.Visitable;

/**
 * 日志缓存监控
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class LogCacheMonitor implements CacheMonitor {

	private static LogVisitor logVisitor = new LogVisitor();

	@Override
	public void monitor() {
		String[] names = CacheFactory.getAllCacheName();
		for (String name : names) {
			Cache<?, ?> cache = CacheFactory.getCache(name);
			if (cache instanceof Visitable) {
				((Visitable) cache).accept(logVisitor, name);
			}
		}
	}

}
