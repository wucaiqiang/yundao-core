package com.yundao.core.cache;

import com.yundao.core.cache.handler.RemoveHandler;
import com.yundao.core.cache.visitor.Visitable;

/**
 * 默认缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public interface DefaultCache<K, V> extends Cache<K, V>, Visitable {

	/**
	 * 删除已过期的对象
	 * 
	 * @param h
	 * @return
	 */
	public int remove(RemoveHandler<K, V> h);

	/**
	 * 获取上一次删除的时间
	 * 
	 * @return
	 */
	public long getLastRemoveTime();

	/**
	 * 获取上一次删除时所耗费的时间
	 * 
	 * @return
	 */
	public long getLastRemoveConsumeTime();

	/**
	 * 获取上一次删除是否成功
	 * 
	 * @return
	 */
	public boolean getLastRemoveStatus();

}
