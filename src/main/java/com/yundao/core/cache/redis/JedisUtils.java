package com.yundao.core.cache.redis;

import com.yundao.core.cache.config.CacheConfigEnum;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.CloseableUtils;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.SerializeUtils;
import org.apache.commons.lang.math.NumberUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jedis工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class JedisUtils {

    private static Log log = LogFactory.getLog(JedisUtils.class);

    private static JedisPool pool;

    static {
        // 设置配置
        JedisPoolConfig config = new JedisPoolConfig();
        log.info("JedisUtils初始化配置开始");
        config.setMaxTotal(NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.MAX_TOTAL.getKey())));
        config.setMaxIdle(NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.MAX_IDLE.getKey())));
        config.setMinIdle(NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.MIN_IDLE.getKey())));
        config.setMaxWaitMillis(NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.MAX_WAIT_MILLIS.getKey())));
        config.setTestOnBorrow(org.apache.commons.lang.BooleanUtils
                .toBoolean(ConfigUtils.getValue(CacheConfigEnum.TEST_ON_BORROW.getKey())));
        config.setBlockWhenExhausted(org.apache.commons.lang.BooleanUtils
                .toBoolean(ConfigUtils.getValue(CacheConfigEnum.BLOCK_WHEN_EXHAUSTED.getKey())));

        // 初始化jedis池
        String host = ConfigUtils.getValue(CacheConfigEnum.HOST.getKey());
        log.info("JedisUtils||host:%s", host);
        int port = NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.PORT.getKey()));
        int timeout = NumberUtils.toInt(ConfigUtils.getValue(CacheConfigEnum.CONNECTION_TIMEOUT.getKey()));
        String password = ConfigUtils.getValue(CacheConfigEnum.PASSWORD.getKey());
        if (!BooleanUtils.isBlank(password)) {
            pool = new JedisPool(config, host, port, timeout, password);
        } else {
            pool = new JedisPool(config, host, port, timeout);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.get(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> V getObject(String key) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }

        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            byte[] data = jedis.get(key.getBytes());
            return (V) SerializeUtils.unserialize(data);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取全部指定键的值
     *
     * @param key
     * @return
     */
    public static List<String> getList(String key) {
        return getList(key, 0, -1);
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> getList(String key, long start, long end) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.lrange(key, start, end);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @return
     */
    public static Set<String> getSet(String key) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.smembers(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> getSortedSet(String key) {
        return getSortedSet(key, 0, -1);
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> getSortedSet(String key, long start, long end) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.zrange(key, start, end);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @return
     */
    public static Map<String, String> getMap(String key) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.hgetAll(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定键的值
     *
     * @param key
     * @param field
     * @return
     */
    public static String getMap(String key, String field) {
        if (BooleanUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            if (jedis == null) {
                return null;
            }
            return jedis.hget(key, field);
        } finally {
            close(jedis);
        }
    }

    /**
     * 是否包含指定的键
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        if (BooleanUtils.isBlank(key)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     */
    public static void setString(String key, String value) {
        if (BooleanUtils.isBlank(key) || value == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     */
    public static void setString(String key, String value, JedisEnum.NXXX nxxx, JedisEnum.EXPX expx, long time) {
        if (BooleanUtils.isBlank(key) || value == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value, nxxx.getValue(), expx.getValue(), time);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     */
    public static void setList(String key, String... value) {
        if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.lpush(key, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     */
    public static void setSet(String key, String... value) {
        if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.sadd(key, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     * @param score
     */
    public static void setSortedSet(String key, String value, double score) {
        if (BooleanUtils.isBlank(key) || value == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.zadd(key, score, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     */
    public static void setSortedSet(String key, Map<String, Double> value) {
        if (BooleanUtils.isBlank(key) || BooleanUtils.isEmpty(value)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.zadd(key, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param field
     * @param value
     */
    public static void setMap(String key, String field, String value) {
        if (BooleanUtils.isBlank(key) || BooleanUtils.isBlank(field)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.hset(key, field, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     */
    public static void setObject(String key, Object value) {
        setObject(key, CommonConstant.NEGATIVE_ONE, value);
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param expireSeconds
     * @param value
     */
    public static void setObject(String key, int expireSeconds, Object value) {
        if (BooleanUtils.isBlank(key) || value == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key.getBytes(), SerializeUtils.serialize(value));
            if (expireSeconds != CommonConstant.NEGATIVE_ONE) {
                jedis.expire(key, expireSeconds);
            }
        } finally {
            close(jedis);
        }
    }

    /**
     * 缓存指定的键值
     *
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     */
    public static String setObject(String key, Object value, JedisEnum.NXXX nxxx, JedisEnum.EXPX expx, long time) {
        if (BooleanUtils.isBlank(key) || value == null) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            return jedis.set(key.getBytes(), SerializeUtils.serialize(value), nxxx.getValue().getBytes(),
                    expx.getValue().getBytes(), time);
        } finally {
            close(jedis);
        }
    }

    /**
     * 批量缓存map中的键值
     *
     * @param map
     */
    public static <V> void batchSetObject(Map<String, V> map) {
        if (BooleanUtils.isEmpty(map)) {
            return;
        }
        Jedis jedis = getJedis();
        Pipeline pipeline = jedis.pipelined();
        try {
            for (Map.Entry<String, V> entry : map.entrySet()) {
                pipeline.set(entry.getKey().getBytes(), SerializeUtils.serialize(entry.getValue()));
            }
            pipeline.sync();
        } finally {
            CloseableUtils.close(pipeline);
            close(jedis);
        }
    }

    /**
     * 增加指定的值
     *
     * @param key
     * @param value
     */
    public static void incrementBy(String key, int value) {
        if (BooleanUtils.isBlank(key) || value == 0) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.incrBy(key, value);
        } finally {
            close(jedis);
        }
    }

    /**
     * 给指定的键设置过期时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {
        if (BooleanUtils.isBlank(key) || seconds == 0) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, seconds);
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除指定的键
     *
     * @param key
     */
    public static void remove(String... key) {
        if (BooleanUtils.isEmpty(key)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取以指定格式的键
     *
     * @param pattern
     * @return
     */
    public static Set<String> keySet(String pattern) {
        if (BooleanUtils.isBlank(pattern)) {
            return null;
        }
        Set<String> result = null;
        Jedis jedis = getJedis();
        try {
            result = jedis.keys(pattern);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 删除指定数据库的键
     *
     * @return
     */
    public static void clear() {
        Jedis jedis = getJedis();
        try {
            jedis.flushDB();
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取jedis对象
     *
     * @return
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 关闭jedis对象
     *
     * @param jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}