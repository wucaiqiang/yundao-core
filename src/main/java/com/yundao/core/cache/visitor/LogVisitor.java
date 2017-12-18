package com.yundao.core.cache.visitor;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.cache.Cache;
import com.yundao.core.cache.DefaultCache;
import com.yundao.core.cache.ReloadCache;

/**
 * 日志访问者
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class LogVisitor implements Visitor {

	private static Log log = LogFactory.getLog(LogVisitor.class);

	@Override
	public void visitCache(Cache<?, ?> cache, String name) {
		StringBuilder result = new StringBuilder();
		this.append(cache, name, result);
		log.info(result.toString());
	}

	@Override
	public void visitReloadCache(ReloadCache<?, ?> cache, String name) {
		long lastReloadTime = cache.getLastReloadTime();
		long lastReloadConsumeTime = cache.getLastReloadConsumeTime();
		boolean lastReloadStatus = cache.getLastReloadStatus();

		StringBuilder result = new StringBuilder();
		this.append(cache, name, result);

		result.append("lastReloadTime=").append(lastReloadTime).append(",");
		result.append("lastReloadConsumeTime=").append(lastReloadConsumeTime).append(",");
		result.append("lastReloadStatus=").append(lastReloadStatus).append(",");
		log.info(result.toString());
	}

	@Override
	public void visitDefaultCache(DefaultCache<?, ?> cache, String name) {
		long lastRemoveTime = cache.getLastRemoveTime();
		long lastRemoveConsumeTime = cache.getLastRemoveConsumeTime();
		boolean lastRemoveStatus = cache.getLastRemoveStatus();

		StringBuilder result = new StringBuilder();
		this.append(cache, name, result);

		result.append("lastRemoveTime=").append(lastRemoveTime).append(",");
		result.append("lastRemoveConsumeTime=").append(lastRemoveConsumeTime).append(",");
		result.append("lastRemoveStatus=").append(lastRemoveStatus).append(",");
		log.info(result.toString());
	}

	private void append(Cache<?, ?> cache, String name, StringBuilder result) {
		long hitCount = cache.getHitCount();
		long queryCount = cache.getQueryCount();
		long size = cache.size();

		result.append("name=").append(name).append(",");
		result.append("hitCount=").append(hitCount).append(",");
		result.append("queryCount=").append(queryCount).append(",");
		result.append("size=").append(size).append(",");
	}
}
