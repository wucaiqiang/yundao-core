package com.yundao.core.cache.config;

import com.google.common.collect.Maps;
import com.yundao.core.cache.Cache;
import com.yundao.core.cache.map.MapDefaultCache;
import com.yundao.core.cache.redis.JedisReloadDefaultCache;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * 缓存配置文件枚举类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public enum CacheConfigEnum {

	/**
	 * 缓存存储的位置，值可以为map、redis
	 */
	STORE_TYPE("store.type"),

	/**
	 * 缓存配置文件的路径
	 */
	CONFIG_XML("config.xml"),

	/**
	 * 缓存删除的间隔，单位秒
	 */
	REMOVE_INTERVAL("remove.interval"),

	/**
	 * 缓存监控的间隔，单位秒
	 */
	MONITOR_INTERVAL("monitor.interval"),

	/**
	 * redis主机
	 */
	HOST("spring.redis.host"),

	/**
	 * redis端口号
	 */
	PORT("spring.redis.port"),

	/**
	 * 密码
	 */
	PASSWORD("spring.redis.password"),

	/**
	 * 连接超时时间
	 */
	CONNECTION_TIMEOUT("spring.redis.timeout"),

	/**
	 * 线程池中最大的数量
	 */
	MAX_TOTAL("spring.redis.pool.max-active"),

	/**
	 * 线程池中空闲对象的最大数量
	 */
	MAX_IDLE("spring.redis.pool.max-idle"),

	/**
	 * 线程池中空闲对象的最小数量
	 */
	MIN_IDLE("spring.redis.pool.min-idle"),

	/**
	 * 获取线程池中对象时的最大等待时间
	 */
	MAX_WAIT_MILLIS("spring.redis.pool.max-wait-millis"),

	/**
	 * 获取对象时是否测试
	 */
	TEST_ON_BORROW("spring.redis.pool.test-on-borrow"),

	/**
	 * 对象池耗完时是否阻塞
	 */
	BLOCK_WHEN_EXHAUSTED("spring.redis.pool.block-when-exhausted");

	private String key;

	private CacheConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@SuppressWarnings("rawtypes")
	public static enum CacheStoreTypeEnum {

		MAP("map") {
			@SuppressWarnings("unchecked")
			@Override
			public Cache newCache(Map<String, Object> map) {
				return new MapDefaultCache(map);
			}
		},

		REDIS("redis") {
			@Override
			public Cache newCache(Map<String, Object> map) {
				return new JedisReloadDefaultCache<Object>(map);
			}
		};

		private String type;
		private static Map<String, CacheStoreTypeEnum> enumMap = Maps.newHashMap();

		static {
			EnumSet<CacheStoreTypeEnum> set = EnumSet.allOf(CacheStoreTypeEnum.class);
			for (CacheStoreTypeEnum each : set) {
				enumMap.put(each.getType(), each);
			}
		}

		private CacheStoreTypeEnum(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

		/**
		 * 获取缓存类型
		 * 
		 * @param type
		 * @return
		 */
		public static CacheStoreTypeEnum getCacheStoreTypeEnum(String type) {
			if (type != null) {
				type = type.toLowerCase();
			}
			return enumMap.get(type);
		}

		/**
		 * 获取当前所支持的类型
		 * 
		 * @return
		 */
		public static String getCacheType() {
			Set<String> set = enumMap.keySet();
			return set.toString();
		}

		/**
		 * 创建缓存
		 * 
		 * @param map
		 * @return
		 */
		public abstract Cache newCache(Map<String, Object> map);
	}
}
