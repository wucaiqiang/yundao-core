package com.yundao.core.cache.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yundao.core.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Tuple;

/**
 * redis缓存类，使用这个就可以了
 * 
 * @author <a href="mailto:JonChiang@zcmall.com">JonChiang</a>
 */
public class RedisCache<K, V> {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

	/**
	 * The wrapped Jedis instance.
	 */
	private RedisManager jedisManager;

	/**
	 * The Redis key prefix for the sessions
	 */
	private String keyPrefix = "W_USER_CENTER:";

	/**
	 * 通过一个JedisManager实例构造RedisCache
	 */
	public RedisCache(RedisManager cache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		cache.init();
		this.jedisManager = cache;
	}

	public RedisCache(RedisManager cache, String prefix) {
		this(cache);
		// set the prefix
		this.keyPrefix = prefix;
	}

	public Long del(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.del(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return 0l;
	}

	public V get(K key) {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				byte[] rawValue = jedisManager.get(getByteKey(key));
				@SuppressWarnings("unchecked")
				V value = (V) SerializeUtils.unserialize(rawValue);
				return value;
			}
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;

	}

	/**
	 * 暂时用老的序列化工具，以后再改
	 * 
	 * @param key
	 * @param type
	 * @return
	 * @throws CacheException
	 */
	public <T> T getAndClass(K key, Class<T> type) {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				byte[] rawValue = jedisManager.get(getByteKey(key));
				T value = (T) SerializeUtils.unserialize(rawValue);
				return value;
			}
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(K key) {
		if (key instanceof String) {
			String preKey = this.keyPrefix + key;
			return preKey.getBytes();
		} else {
			return SerializeUtils.serialize(key);
		}
	}

	public String getStr(String key) {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				return jedisManager.get(this.keyPrefix + key);
			}
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public Boolean hexists(String key, String field) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.hexists(key, field);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public String hmset(String key, Map<String, String> hash) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.hmset(key, hash);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public Long hsetObj(String key, String field, String value) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}
 
	/*------------------------------------------------ map  操作 ------------------------------------------------*/
	public Long llen(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.llen(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * list左边出队列
	 * 
	 * @author jon
	 * @date 2015年8月12日
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.lpop(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 往list里面放置值
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long lpush(String key, String... strings) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.lpush(key, strings);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/******************************************* 新增集合操方法 ****************************************/

	/**** List 操作 ****/

	/**
	 * 往list里面放置值
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long lpushx(String key, String... strings) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.lpushx(key, strings);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 按下表取list
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, int start, int end) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.lrange(key, start, end);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 去list所有值
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @return
	 */
	public List<String> lrangeAll(String key) {
		return lrange(key, 0, -1);
	}

	public String lremove(String key, long start, long end) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.lremove(key, start, end);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public V put(K key, V value) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(getByteKey(key), SerializeUtils.serialize(value));
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public V put(K key, V value, int expire) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(getByteKey(key), SerializeUtils.serialize(value), expire);
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public V putAndClass(K key, V value){
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(getByteKey(key), SerializeUtils.serialize(value));
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public void putStr(String key) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(this.keyPrefix + key, "1");
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
	}

	/*------------------------------------------------ set操作 ------------------------------------------------*/

	public void putStr(String key, int expire) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(this.keyPrefix + key, "1", expire);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
	}

	public String putStr(String key, String value) {
		logger.debug("根据key从存储 key [" + this.keyPrefix + key + "]");
		try {
			jedisManager.set(this.keyPrefix + key, value);
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public String putStr(String key, String value, int expire) {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisManager.set(this.keyPrefix + key, value, expire);
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/*------------------------------------------------ 排序有序set 操作 ------------------------------------------------*/

	public void removeStr(String key) {
		key = this.keyPrefix + key;
		logger.debug("从redis中删除 key [" + key + "]");
		try {
			jedisManager.del(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
	}

	/**
	 * list右边出队列
	 * 
	 * @author jon
	 * @date 2015年8月12日
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.rpop(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * @param key
	 * @param array
	 */
	public void sadd(String key, Integer... array) {
		// TODO Auto-generated method stub

	}

	public Long sadd(String key, String... value) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.sadd(key, value);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public Long scard(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.scard(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public Boolean sismember(String key, String field) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.sismember(key, field);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}
	public Set<String> sismembers(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.sismembers(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	public Long srem(String key, String... value) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.srem(key, value);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	 

	/**
	 * 插入排序set
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zadd(key, score, member);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 插入一个结合
	 * 
	 * @author jon
	 * @date 2015年7月21日
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zadd(key, scoreMembers);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 查count
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zcard(String key) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zcard(key);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 根据排名删除
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, long start, long end) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zremrangeByRank(key, start, end);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 根据下标获取set
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zrevrange(key, start, end);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}

	/**
	 * 根据下标获取set
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		key = this.keyPrefix + key;
		try {
			return jedisManager.zrevrangeWithScores(key, start, end);
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}
	/**
	 * 记数
	 * @param key
	 * @return
	 */
	public Long incr(String key) {
		key = this.keyPrefix + key;
		try {
			Long value = jedisManager.incr(key);
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}
	/**
	 * 记数
	 * @param key
	 * @return
	 */
	public Long incr(String key,int seconds) {
		key = this.keyPrefix + key;
		try {
			Long value = jedisManager.incr(key,seconds);
			return value;
		} catch (Throwable t) {
			logger.error("redis异常 key="+key, t);
		}
		return null;
	}
}
