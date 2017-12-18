package com.yundao.core.cache.visitor;

import com.yundao.core.cache.Cache;
import com.yundao.core.cache.DefaultCache;
import com.yundao.core.cache.ReloadCache;

/**
 * 访问者
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public interface Visitor {

	/**
	 * 访问缓存
	 * 
	 * @param cache
	 * @param name
	 */
	public void visitCache(Cache<?, ?> cache, String name);

	/**
	 * 访问可加载缓存
	 * 
	 * @param cache
	 * @param name
	 */
	public void visitReloadCache(ReloadCache<?, ?> cache, String name);

	/**
	 * 访问默认缓存
	 * 
	 * @param cache
	 * @param name
	 */
	public void visitDefaultCache(DefaultCache<?, ?> cache, String name);
}
