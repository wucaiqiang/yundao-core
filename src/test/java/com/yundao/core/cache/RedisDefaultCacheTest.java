package com.yundao.core.cache;

import org.junit.Assert;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * redis默认缓存测试
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class RedisDefaultCacheTest {

	private static Log log = LogFactory.getLog(RedisDefaultCacheTest.class);
	private static Cache<String, String> cache = CacheFactory.getCache("RedisDefaultCacheTest");

	//@Test
	public void testCache() {
		cache.put("10", "10 seconds live");
		Assert.assertEquals("10 seconds live", cache.get("10"));
		try {
			Thread.sleep(10 * 1000);
		}
		catch (InterruptedException e) {
			log.error("线程睡眠时异常", e);
		}
		Assert.assertNotEquals("10 seconds live", cache.get("10"));
	}

}
