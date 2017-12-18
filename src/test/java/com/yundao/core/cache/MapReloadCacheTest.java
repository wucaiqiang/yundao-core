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
public class MapReloadCacheTest extends MapReloadCache<String, String> {

	private static Cache<String, String> cache = CacheFactory.getCache("MapReloadCacheTest");

	@Override
	public Map<String, String> doReload() {
		Map<String, String> map = Maps.newHashMap();
		map.put("111", "aaa_map");
		map.put("222", "bbb_map");
		map.put("333", "ccc_map");
		map.put("444", "ddd_map");
		return map;
	}

	@Test
	public void testCache() {
		Assert.assertEquals("aaa_map", cache.get("111"));
		Assert.assertEquals("bbb_map", cache.get("222"));
		Assert.assertEquals("ccc_map", cache.get("333"));
		Assert.assertEquals("ddd_map", cache.get("444"));
	}

}
