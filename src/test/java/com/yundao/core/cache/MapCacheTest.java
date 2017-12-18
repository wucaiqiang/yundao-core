package com.yundao.core.cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * map缓存测试
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class MapCacheTest {

	private static Cache<String, String> mapCacheNest = CacheFactory.getCache("MapReloadCacheNestTest");

	@Test
	public void testCache() {
		Assert.assertEquals("aaa_map_nest", mapCacheNest.get("111"));
	}
}
