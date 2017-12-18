package com.yundao.core.cache.redis;

import java.util.Map;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * redis加载时默认缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class JedisReloadDefaultCache<V> extends JedisReloadCache<V> {

	private static Log log = LogFactory.getLog(JedisReloadDefaultCache.class);

	private long timeout = -1;

	public JedisReloadDefaultCache() {
		this(null);
	}

	public JedisReloadDefaultCache(Map<String, Object> map) {
		init(map);
	}

	private void init(Map<String, Object> map) {
		log.begin("初始化jedis默认缓存，map=", map);
		if (!BooleanUtils.isEmpty(map)) {
			Object timeoutMap = map.get("timeout");
			if (timeoutMap != null) {
				timeout = NumberUtils.toLong(timeoutMap.toString());
			}
		}
		log.end();
	}

	@Override
	public V put(String key, V value) {
		if (timeout <= 0) {
			return super.put(key, value);
		}
		return put(key, value, timeout);
	}

	@Override
	public V put(String key, V value, long expireSeconds) {
		return super.put(key, value, expireSeconds);
	}

	@Override
	public V put(String key, V value, JedisEnum.NXXX nxxx, JedisEnum.EXPX expx, long time) {
		return super.put(key, value, nxxx, expx, time);
	}

	@Override
	public void incrementBy(String key, int value) {
		key = this.getKey(key);
		boolean isExist = JedisUtils.containsKey(key);
		JedisUtils.incrementBy(key, value);
		if (!isExist) {
			JedisUtils.expire(key, ((Long) timeout).intValue());
		}
	}

	@Override
	public Map<String, V> doReload() {
		return null;
	}

}
