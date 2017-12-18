package com.yundao.core.cache;

import java.util.Map;

import org.junit.Assert;

import com.google.common.collect.Maps;
import com.yundao.core.cache.redis.JedisReloadCache;

/**
 * redis缓存测试
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class RedisReloadCacheTest extends JedisReloadCache<String> {

	private static Cache<String, String> cache = CacheFactory.getCache("RedisReloadCacheTest");

	@Override
	public Map<String, String> doReload() {
		Map<String, String> map = Maps.newHashMap();
		map.put("1111", "aaa_redis");
		map.put("2222", "bbb_redis");
		map.put("3333", "ccc_redis");
		map.put("4444", "ddd_redis");
		return map;
	}

	//@Test
	public void testCache() {
		Assert.assertEquals("aaa_redis", cache.get("1111"));
		Assert.assertEquals("bbb_redis", cache.get("2222"));
		Assert.assertEquals("ccc_redis", cache.get("3333"));
		Assert.assertEquals("ddd_redis", cache.get("4444"));
	}

}
