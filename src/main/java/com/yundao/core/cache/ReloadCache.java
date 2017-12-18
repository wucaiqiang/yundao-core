package com.yundao.core.cache;

import com.yundao.core.cache.visitor.Visitable;

/**
 * 初始化时自动加载的缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public interface ReloadCache<K, V> extends Cache<K, V>, Visitable {

	/**
	 * 加载缓存
	 */
	public void reload();

	/**
	 * 获取上一次加载的时间
	 * 
	 * @return
	 */
	public long getLastReloadTime();

	/**
	 * 获取上一次加载所耗费的时间
	 * 
	 * @return
	 */
	public long getLastReloadConsumeTime();

	/**
	 * 获取上一次是否加载成功
	 * 
	 * @return
	 */
	public boolean getLastReloadStatus();

}
