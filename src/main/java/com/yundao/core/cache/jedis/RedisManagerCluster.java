package com.yundao.core.cache.jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yundao.core.utils.BooleanUtils;

import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

/**
 * 集群版本
 * 
 * @author <a href="mailto:JonChiang@zcmall.com">JonChiang</a>
 */
public class RedisManagerCluster {

	// private static Logger logger =
	// LoggerFactory.getLogger(RedisManager.class);

	private String host1;
	private int port1;
	private String host2;
	private int port2;
	private String host3;
	private int port3;

	private String password;

	// 0 - never expire
	private int expire = 0;
	// private int db = 0;

	/**
	 * @return Returns the host1.
	 */
	public String getHost1() {
		return host1;
	}

	/**
	 * @param host1
	 *            The host1 to set.
	 */
	public void setHost1(String host1) {
		this.host1 = host1;
	}

	/**
	 * @return Returns the port1.
	 */
	public int getPort1() {
		return port1;
	}

	/**
	 * @param port1
	 *            The port1 to set.
	 */
	public void setPort1(int port1) {
		this.port1 = port1;
	}

	/**
	 * @return Returns the host2.
	 */
	public String getHost2() {
		return host2;
	}

	/**
	 * @param host2
	 *            The host2 to set.
	 */
	public void setHost2(String host2) {
		this.host2 = host2;
	}

	/**
	 * @return Returns the port2.
	 */
	public int getPort2() {
		return port2;
	}

	/**
	 * @param port2
	 *            The port2 to set.
	 */
	public void setPort2(int port2) {
		this.port2 = port2;
	}

	/**
	 * @return Returns the host3.
	 */
	public String getHost3() {
		return host3;
	}

	/**
	 * @param host3
	 *            The host3 to set.
	 */
	public void setHost3(String host3) {
		this.host3 = host3;
	}

	/**
	 * @return Returns the port3.
	 */
	public int getPort3() {
		return port3;
	}

	/**
	 * @param port3
	 *            The port3 to set.
	 */
	public void setPort3(int port3) {
		this.port3 = port3;
	}

	/**
	 * @return Returns the expire.
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * @param expire
	 *            The expire to set.
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

	private static JedisCluster jedisCluster = null;

	
	public void init() {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		// Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort(host1, port1));
		if (!BooleanUtils.isEmpty(host2)) {
			jedisClusterNodes.add(new HostAndPort(host2, port2));
		}
		if (!BooleanUtils.isEmpty(host3)) {
			jedisClusterNodes.add(new HostAndPort(host3, port3));
		}

		jedisCluster = new JedisCluster(jedisClusterNodes);
	}

	/**
	 * @see com.welab.redis.IRedisManager#get(byte[])
	 */
	
	public byte[] get(byte[] key) {
		byte[] value = null;
		value = jedisCluster.get(key);

		return value;
	}

	/**
	 * @see com.welab.redis.IRedisManager#get(java.lang.String)
	 */
	
	public String get(String key) {
		String value = null;
		value = jedisCluster.get(key);
		return value;
	}

	/**
	 * @see com.welab.redis.IRedisManager#set(byte[], byte[])
	 */
	
	public byte[] set(byte[] key, byte[] value) {
		jedisCluster.set(key, value);
		if (this.expire != 0) {
			jedisCluster.expire(key, this.expire);
		}

		return value;
	}

	/**
	 * @see com.welab.redis.IRedisManager#set(java.lang.String,
	 *      java.lang.String)
	 */
	
	public String set(String key, String value) {
		jedisCluster.set(key, value);
		if (this.expire != 0) {
			jedisCluster.expire(key, this.expire);
		}

		return value;
	}

	/**
	 * @see com.welab.redis.IRedisManager#set(byte[], byte[], int)
	 */
	
	public byte[] set(byte[] key, byte[] value, int expire) {
		jedisCluster.set(key, value);
		if (expire != 0) {
			jedisCluster.expire(key, expire);
		}

		return value;
	}

	/**
	 * @see com.welab.redis.IRedisManager#set(java.lang.String,
	 *      java.lang.String, int)
	 */
	
	public String set(String key, String value, int expire) {
		jedisCluster.set(key, value);
		if (expire != 0) {
			jedisCluster.expire(key, expire);
		}

		return value;
	}

	/**
	 * @return
	 * @see com.welab.redis.IRedisManager#del(byte[])
	 */
	
	public Long del(byte[] key) {
		return jedisCluster.del(key);

	}

	/**
	 * @return
	 * @see com.welab.redis.IRedisManager#del(java.lang.String)
	 */
	
	public Long del(String key) {
		return jedisCluster.del(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#flushDB()
	 */
	
	@Deprecated
	public void flushDB() {
		// jedisCluster.flushDB();

	}

	/**
	 * @see com.welab.redis.IRedisManager#dbSize()
	 */
	
	@Deprecated
	public Long dbSize() {
		Long dbSize = 0L;

		// dbSize = jedisCluster.dbSize();

		return dbSize;
	}

	/**
	 * @see com.welab.redis.IRedisManager#keys(java.lang.String)
	 */
	
	public Set<byte[]> keys(String pattern) {
		// TODO
		return null;
	}

	/******************************************* 新增集合操方法 ****************************************/

	/**** List 操作 ****/

	/**
	 * @see com.welab.redis.IRedisManager#lpush(java.lang.String,
	 *      java.lang.String)
	 */
	
	public long lpush(String key, String... strings) {
		return jedisCluster.lpush(key, strings);

	}

	/**
	 * @see com.welab.redis.IRedisManager#lpop(java.lang.String)
	 */
	
	public String lpop(String key) {
		return jedisCluster.lpop(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#rpop(java.lang.String)
	 */
	
	public String rpop(String key) {
		return jedisCluster.rpop(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#lpushx(java.lang.String,
	 *      java.lang.String)
	 */
	
	public long lpushx(String key, String... strings) {
		return jedisCluster.lpushx(key, strings);

	}

	/**
	 * @see com.welab.redis.IRedisManager#lrange(java.lang.String, long, long)
	 */
	
	public List<String> lrange(String key, long start, long end) {
		return jedisCluster.lrange(key, start, end);

	}

	/**
	 * @see com.welab.redis.IRedisManager#llen(java.lang.String)
	 */
	
	public Long llen(String key) {
		return jedisCluster.llen(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#lremove(java.lang.String, long, long)
	 */
	
	public String lremove(String key, long start, long end) {
		return jedisCluster.ltrim(key, start, end);

	}

	/**** 排序set 操作 ****/

	/**
	 * @see com.welab.redis.IRedisManager#zadd(java.lang.String, double,
	 *      java.lang.String)
	 */
	
	public Long zadd(String key, double score, String member) {
		return jedisCluster.zadd(key, score, member);

	}

	/**
	 * @see com.welab.redis.IRedisManager#zadd(java.lang.String, java.util.Map)
	 */
	
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return jedisCluster.zadd(key, scoreMembers);

	}

	/**
	 * @see com.welab.redis.IRedisManager#zcard(java.lang.String)
	 */
	
	public Long zcard(String key) {
		return jedisCluster.zcard(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#zremrangeByRank(java.lang.String,
	 *      long, long)
	 */
	
	public Long zremrangeByRank(String key, long start, long end) {
		return jedisCluster.zremrangeByRank(key, start, end);

	}

	/**
	 * @see com.welab.redis.IRedisManager#zrevrangeWithScores(java.lang.String,
	 *      long, long)
	 */
	
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return jedisCluster.zrevrangeWithScores(key, start, end);

	}

	/**
	 * @see com.welab.redis.IRedisManager#zrevrange(java.lang.String, long,
	 *      long)
	 */
	
	public Set<String> zrevrange(String key, long start, long end) {
		return jedisCluster.zrevrange(key, start, end);

	}

	/*------------------------------------------------ map  操作 ------------------------------------------------*/
	/**
	 * @see com.welab.redis.IRedisManager#hset(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	
	public Long hset(final String key, final String field, final String value) {
		return jedisCluster.hset(key, field, value);

	}

	
	public Boolean hexists(final String key, final String field) {
		return jedisCluster.hexists(key, field);

	}

	
	public Boolean sismember(final String key, final String field) {
		return jedisCluster.sismember(key, field);

	}

	
	public Long scard(final String key) {
		return jedisCluster.scard(key);

	}

	/**
	 * @see com.welab.redis.IRedisManager#hmset(java.lang.String, java.util.Map)
	 */
	
	public String hmset(final String key, final Map<String, String> hash) {
		return jedisCluster.hmset(key, hash);

	}

	
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor, final int count) {
		ScanParams scanParams = new ScanParams();
		scanParams.count(count);
		jedisCluster.hscan(key, cursor, scanParams);
		return jedisCluster.hscan(key, cursor);

	}

	
	public ScanResult<String> sscan(final String key, final String cursor, final int count) {
		ScanParams scanParams = new ScanParams();
		scanParams.count(count);
		jedisCluster.sscan(key, cursor, scanParams);
		return jedisCluster.sscan(key, cursor);

	}

	
	public Long sadd(final String key, final String... members) {
		return jedisCluster.sadd(key, members);

	}

	
	public Long srem(final String key, final String... members) {
		return jedisCluster.srem(key, members);

	}

}
