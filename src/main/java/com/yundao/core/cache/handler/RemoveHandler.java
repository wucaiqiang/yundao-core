package com.yundao.core.cache.handler;

import com.yundao.core.cache.list.Node;

/**
 * 删除缓存时的处理类
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public interface RemoveHandler<K, V> {

	/**
	 * 处理节点
	 * 
	 * @param n
	 * @return
	 */
	public boolean handle(Node<K, V> n);
}