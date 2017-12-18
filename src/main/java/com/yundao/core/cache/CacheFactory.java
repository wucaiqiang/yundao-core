package com.yundao.core.cache;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Maps;
import com.yundao.core.cache.config.CacheConfigEnum;
import com.yundao.core.cache.config.CacheConfigEnum.CacheStoreTypeEnum;
import com.yundao.core.cache.monitor.CacheMonitor;
import com.yundao.core.cache.monitor.LogCacheMonitor;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.DomUtils;

/**
 * 缓存工厂类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class CacheFactory {

	private static Log log = LogFactory.getLog(CacheFactory.class);

	private static Map<String, CacheWrapper> cacheMap = new ConcurrentHashMap<String, CacheWrapper>();

	private static volatile boolean isCacheMonitorStop = true;
	private static CacheMonitor cacheMonitor = new LogCacheMonitor();

	private static long cacheMonitorInterval = NumberUtils
			.toLong(ConfigUtils.getValue(CacheConfigEnum.MONITOR_INTERVAL.getKey()));
	private static long cacheRemoveInterval = NumberUtils
			.toLong(ConfigUtils.getValue(CacheConfigEnum.REMOVE_INTERVAL.getKey()));

	private static Thread cacheRemoveThread = new Thread() {
		@Override
		public void run() {
			remove();
		}
	};

	// private static Thread cacheMonitorThread = new Thread() {
	// @Override
	// public void run() {
	// monitor();
	// }
	// };

	static {
		int oneMinute = 1 * 60;
		if (cacheMonitorInterval <= 0) {
			cacheMonitorInterval = oneMinute;
		}
		if (cacheRemoveInterval <= 0) {
			cacheRemoveInterval = oneMinute;
		}
		cacheMonitorInterval *= 1000;
		cacheRemoveInterval *= 1000;

		load(ConfigUtils.getValue(CacheConfigEnum.CONFIG_XML.getKey()));
		startCacheMonitor();

		cacheRemoveThread.setDaemon(true);
		cacheRemoveThread.start();

		// cacheMonitorThread.setDaemon(true);
		// cacheMonitorThread.start();
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param name
	 * @return
	 */
	public static <K, V> Cache<K, V> getCache(String name) {
		CacheWrapper cw = cacheMap.get(name);
		if (cw != null) {
			return cw.getCache();
		}
		return null;
	}

	/**
	 * 获取所有缓存名字
	 * 
	 * @return
	 */
	public static String[] getAllCacheName() {
		return cacheMap.keySet().toArray(new String[0]);
	}

	/**
	 * 获取缓存监控类
	 * 
	 * @return
	 */
	public static CacheMonitor getCacheMonitor() {
		return cacheMonitor;
	}

	/**
	 * 获取缓存监控间隔
	 * 
	 * @return
	 */
	public static long getCacheMonitorInterval() {
		return cacheMonitorInterval;
	}

	/**
	 * 设置缓存监控
	 * 
	 * @param cacheMonitor
	 * @param cacheMonitorInterval
	 */
	public static void setCacheMonitor(CacheMonitor cacheMonitor, long cacheMonitorInterval) {
		log.begin("设置缓存监控， cacheMonitor=", cacheMonitor.getClass().getName(), "，cacheMonitorInterval=",
				cacheMonitorInterval);
		if (CacheFactory.cacheMonitor != cacheMonitor) {
			CacheFactory.cacheMonitor = cacheMonitor;
		}
		if (CacheFactory.cacheMonitorInterval != cacheMonitorInterval) {
			CacheFactory.cacheMonitorInterval = cacheMonitorInterval;
		}
	}

	/**
	 * 停止缓存监控
	 */
	public static void stopCacheMonitor() {
		if (!isCacheMonitorStop) {
			log.info("停止缓存监控");
			isCacheMonitorStop = true;
		}
	}

	/**
	 * 启动缓存监控
	 */
	public static void startCacheMonitor() {
		if (isCacheMonitorStop) {
			log.info("启动缓存监控");
			isCacheMonitorStop = false;
		}
	}

	/**
	 * 设置缓存删除间隔
	 * 
	 * @param cacheRemoveInterval
	 */
	public static void setCacheRemoveInterval(long cacheRemoveInterval) {
		CacheFactory.cacheRemoveInterval = cacheRemoveInterval;
	}

	/**
	 * 加载配置文件
	 * 
	 * @param fileName
	 */
	private static void load(String fileName) {
		log.begin("缓存配置文件名称，fileName=" + fileName);
		try {
			Document document = DomUtils.getDocument(fileName);
			List<Element> elementList = DomUtils.getElementsByName(document, "cache");
			int size = elementList.size();

			// 设置缓存代理
			for (int i = 0; i < size; i++) {
				Element node = elementList.get(i);
				String name = node.getAttribute("name");

				// 校验缓存名字是否已存在
				if (cacheMap.containsKey(name)) {
					log.error("缓存名字已存在，请更改， name=" + name);
					continue;
				}

				CacheWrapper cw = new CacheWrapper(new CacheProxy(), name);
				cw.setRemoveInterval(
						NumberUtils.toLong(DomUtils.getConfigValueByName(node, "removeInterval", "value")));
				cacheMap.put(name, cw);
			}

			// 初始化
			for (int i = 0; i < size; i++) {
				Element node = elementList.get(i);
				String name = node.getAttribute("name");
				log.info("开始创建缓存，name=" + name);

				Cache cache = newCache(node);
				cache.setName(name);

				CacheWrapper cw = cacheMap.get(name);
				CacheProxy cacheProxy = (CacheProxy) cw.getCache();
				cacheProxy.setCache(cache);
				cw.setCache(cache);

				if (cache instanceof ReloadCache) {
					((ReloadCache) cache).reload();
				}
			}
		}
		catch (Throwable t) {
			log.error("加载缓存配置文件时发生异常", t);
		}
		log.end();
	}

	/**
	 * 创建缓存对象
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private static Cache newCache(Element node) throws Exception {
		// 用提供的类名实例化
		Element classElement = DomUtils.getElementByName(node, "class");
		if (classElement != null) {
			String className = classElement.getAttribute("name");
			log.info("缓存类, className=" + className);

			Class clazz = ClassUtils.getClass(className);
			Constructor[] cons = clazz.getConstructors();
			for (Constructor each : cons) {
				Class[] paramTypes = each.getParameterTypes();
				if (paramTypes.length == 0) {
					log.info("用默认构造函数创建缓存, className=" + className);
					return (Cache) each.newInstance();
				}
				else {
					Map<String, Object> params = Maps.newHashMap();
					List<Element> mapList = DomUtils.getElementsByName(classElement, "map");
					for (Element map : mapList) {
						params.put(map.getAttribute("key"), map.getAttribute("value"));
					}
					log.info("用带map的构造函数创建缓存, className=" + className);
					return (Cache) each.newInstance(params);
				}
			}
		}

		// 获取缓存存储类型
		String storeType = node.getAttribute("storeType");
		if (BooleanUtils.isBlank(storeType)) {
			storeType = ConfigUtils.getValue(CacheConfigEnum.STORE_TYPE.getKey());
		}
		CacheStoreTypeEnum cste = CacheStoreTypeEnum.getCacheStoreTypeEnum(storeType);
		if (cste != null) {
			long timeout = NumberUtils.toLong(DomUtils.getConfigValueByName(node, "timeout", "value"), -1);
			int maxSize = NumberUtils.toInt(DomUtils.getConfigValueByName(node, "maxSize", "value"), -1);
			String removeHandler = DomUtils.getConfigValueByName(node, "removeHandler", "value");
			log.info("用指定的缓存类型创建默认缓存, timeout=" + timeout + ",maxSize=" + maxSize + ", storeType=" + storeType
					+ ", removeHandler=" + removeHandler);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeout", timeout);
			map.put("maxSize", maxSize);
			map.put("removeHandler", removeHandler);
			return cste.newCache(map);
		}
		log.error("缓存类型错误，storeType=" + storeType + ", 目前仅支持以下类型：" + CacheStoreTypeEnum.getCacheType());
		return null;
	}

	private static void remove() {
		while (true) {
			try {
				Collection<CacheWrapper> collection = cacheMap.values();
				for (CacheWrapper each : collection) {
					each.remove();
				}
				Thread.sleep(cacheRemoveInterval);
			}
			catch (Exception e) {
				log.error("删除缓存时发生异常", e);
			}
		}
	}

	// private static void monitor() {
	// while (!isCacheMonitorStop) {
	// try {
	// cacheMonitor.monitor();
	// Thread.sleep(cacheMonitorInterval);
	// }
	// catch (Exception e) {
	// log.error("监控缓存时发生异常", e);
	// }
	// }
	// }
}
