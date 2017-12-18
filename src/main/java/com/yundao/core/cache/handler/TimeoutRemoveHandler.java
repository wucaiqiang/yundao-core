package com.yundao.core.cache.handler;

import com.yundao.core.cache.list.Node;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 超时时删除缓存的处理类
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public class TimeoutRemoveHandler<K, V> implements RemoveHandler<K, V> {

	private static Log log = LogFactory.getLog(TimeoutRemoveHandler.class);

	private long timeout;

	public TimeoutRemoveHandler(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public boolean handle(Node<K, V> n) {
		if (n == null || timeout <= 0) {
			log.info("节点为空或超时间隔小于0, timeout=" + timeout);
			return false;
		}
		if (System.currentTimeMillis() - n.getCreateTime() > timeout) {
			return true;
		}
		return false;
	}
}