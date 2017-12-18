package com.yundao.core.cache;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.yundao.core.cache.map.MapReloadCache;

/**
 * map缓存测试
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class MapReloadCacheNestTest extends MapReloadCache<String, String> {

	private static Cache<String, String> cache = CacheFactory.getCache("MapReloadCacheTest");

	@Override
	public Map<String, String> doReload() {
		Map<String, String> map = Maps.newHashMap();
		map.put("111", "aaa_map_nest");
		return map;
	}

	@Test
	public void testCache() {
		Assert.assertEquals("bbb_map", cache.get("222"));
	}
}
