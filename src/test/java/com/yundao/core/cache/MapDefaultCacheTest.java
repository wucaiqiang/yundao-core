package com.yundao.core.cache;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * redis默认缓存测试
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class MapDefaultCacheTest {

	private static Log log = LogFactory.getLog(MapDefaultCacheTest.class);
	private static Cache<String, String> cache = CacheFactory.getCache("MapDefaultCacheTest");

	@Test
	public void testCache() {
		cache.put("10", "10 seconds live");
		Assert.assertEquals("10 seconds live", cache.get("10"));
		Assert.assertEquals("10 seconds live", cache.get("10"));
		try {
			Thread.sleep(11 * 1000);
		}
		catch (InterruptedException e) {
			log.error("线程睡眠时异常", e);
		}
		Assert.assertEquals(null, cache.get("10"));
	}

}
