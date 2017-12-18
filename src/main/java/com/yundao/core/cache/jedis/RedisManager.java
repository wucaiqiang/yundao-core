package com.yundao.core.cache.jedis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.yundao.core.utils.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

/**
 * 普通版本
 * 
 * @author <a href="mailto:JonChiang@zcmall.com">JonChiang</a>
 */
public class RedisManager {

	private static Logger logger = LoggerFactory.getLogger(RedisManager.class);


	private String host;
	private int port;

	private String password;

	// 0 - never expire
	private int expire = 0;

	private int db = 0;

	JedisPool jedisPool;

	public void init() {
		if (null == host || 0 == port) {
			logger.error("请初始化redis配置文件！");
			throw new NullPointerException("找不到redis配置");
		}
		if (jedisPool == null) {
			// jedisPool = JedisUtil.getJedisPool();
			if(BooleanUtils.isBlank(password)){
				password = null;
			}
			jedisPool = new JedisPool(new JedisPoolConfig(), host, port, expire, password, db);
		}
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the expire
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * @param expire
	 *            the expire to set
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String set(String key, String value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * del
	 * 
	 * @param key
	 * @return
	 */
	public Long del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * del
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * flush
	 */
	public void flushDB() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.flushDB();
		} finally {
			jedis.close();
		}
	}

	/**
	 * size
	 */
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			dbSize = jedis.dbSize();
		} finally {
			jedis.close();
		}
		return dbSize;
	}

	/**
	 * keys
	 * 
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			jedis.close();
		}
		return keys;
	}

	/******************************************* 新增集合操方法 ****************************************/

	/**** List 操作 ****/

	/**
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param strings
	 * @return
	 */
	public long lpush(String key, String... strings) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.lpush(key, strings);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 出队列
	 * 
	 * @author jon
	 * @date 2015年8月12日
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.lpop(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 出队列
	 * 
	 * @author jon
	 * @date 2015年8月12日
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.rpop(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param strings
	 * @return
	 */
	public long lpushx(String key, String... strings) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.lpushx(key, strings);
		} finally {
			jedis.close();
		}
	}

	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.lrange(key, start, end);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 
	 * @author jon
	 * @date 2015年7月15日
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long llen(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.llen(key);
		} finally {
			jedis.close();
		}
	}

	public String lremove(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.ltrim(key, start, end);
		} finally {
			jedis.close();
		}
	}

	/**** 排序set 操作 ****/

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
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zadd(key, score, member);
		} finally {
			jedis.close();
		}
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
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zadd(key, scoreMembers);
		} finally {
			jedis.close();
		}
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
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zcard(key);
		} finally {
			jedis.close();
		}
	}

	public Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zremrangeByRank(key, start, end);
		} finally {
			jedis.close();
		}
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
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} finally {
			jedis.close();
		}
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
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.zrevrange(key, start, end);
		} finally {
			jedis.close();
		}
	}

	/*------------------------------------------------ map  操作 ------------------------------------------------*/
	/**
	 * set 单一值到map
	 * 
	 * @author jon
	 * @date 2015年7月23日
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hset(final String key, final String field, final String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hset(key, field, value);
		} finally {
			jedis.close();
		}
	}

	public String hmset(final String key, final Map<String, String> hash) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hmset(key, hash);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 保存到map
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public String hset(final byte key[], final Map<byte[], byte[]> hash) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hmset(key, hash);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 保存到map
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */

	public Long hset(final byte[] key, final byte[] field, final byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hset(key, field, value);
		} finally {
			jedis.close();
		}
	}

	public Boolean hexists(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hexists(key, field);
		} finally {
			jedis.close();
		}
	}

	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hscan(key, cursor);
		} finally {
			jedis.close();
		}
	}

	public Long sadd(String key, String... params) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.sadd(key, params);
		} finally {
			jedis.close();
		}
	}

	public Long scard(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.scard(key);
		} finally {
			jedis.close();
		}
	}

	public Boolean sismember(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.sismember(key, field);
		} finally {
			jedis.close();
		}
	}
	public Set<String> sismembers(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.smembers(key);
		} finally {
			jedis.close();
		}
	}


	public Long srem(String key, String... members) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.srem(key, members);
		} finally {
			jedis.close();
		}
	}
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long value = jedis.incr(key);
			return value;
		} finally {
			jedis.close();
		}
	}
	public Long incr(String key,int seconds) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long value = jedis.incr(key);
			jedis.expire(key,seconds);
			return value;
		} finally {
			jedis.close();
		}
	}
	public Long incr(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.incr(key);
		} finally {
			jedis.close();
		}
	}
}
