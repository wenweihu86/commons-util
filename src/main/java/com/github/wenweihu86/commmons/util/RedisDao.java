package com.github.wenweihu86.commmons.util;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对redis的使用封装，基于jedis
 */

@Setter
@Getter
public class RedisDao {
    private static final Logger LOG = LoggerFactory.getLogger(RedisDao.class);

    // 连接池配置，同apache commons pool配置
    private JedisPoolConfig redisPoolConfig = new JedisPoolConfig();
    private String host;
    private Integer port;
    // 连接+读写超时时间，单位毫秒
    private Integer timeoutMs = 500;
    private JedisPool redisPool;

    public void init() {
        redisPool = new JedisPool(redisPoolConfig, host, port, timeoutMs);
    }

    public void close() {
        if (redisPool != null) {
            redisPool.close();
        }
    }

    // 普通kv操作

    public String set(final String key, final String value) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    /**
     * 同时设置kv、过期时间以及判断key是否已存在，2.6.12版本以后才支持
     * @param key
     * @param value
     * @param nxxx 取值为"NX"或"XX"，"NX"表示key不存在时才设置，"XX"表示key存在时才设置
     * @param expx key过期时间的单位，取值为"EX"或"PX"，"EX"表示单位为妙，"PX"表示单位为毫秒
     * @param time key过期时间
     * @return "OK"表示成功，其他表示失败
     */
    public String set(final String key, final String value,
                      final String nxxx, final String expx, final long time) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.set(key, value, nxxx, expx, time);
        }
    }

    public String get(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.get(key);
        }
    }

    public Long del(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.del(key);
        }
    }

    // hash操作

    public Long hset(final String key, final String field, final String value) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.hset(key, field, value);
        }
    }

    public String hget(final String key, final String field) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    public String hmset(final String key, final Map<String, String> hash) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.hmset(key, hash);
        }
    }

    public List<String> hmget(final String key, final String... fields) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.hmget(key, fields);
        }
    }

    public Map<String, String> hgetAll(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    // list操作

    public Long lpush(final String key, final String... values) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lpush(key);
        }
    }

    public Long llen(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.llen(key);
        }
    }

    public List<String> lrange(final String key, final long start, final long end) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lrange(key, start, end);
        }
    }

    public String lindex(final String key, final long index) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lindex(key, index);
        }
    }

    public String lset(final String key, final long index, final String value) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lset(key, index, value);
        }
    }

    public Long lrem(final String key, final long count, final String value) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lrem(key, count, value);
        }
    }

    public String lpop(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.lpop(key);
        }
    }

    public String rpop(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.rpop(key);
        }
    }

    // set操作

    public Long sadd(final String key, final String... member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.sadd(key, member);
        }
    }

    public Set<String> smembers(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.smembers(key);
        }
    }

    public Long srem(final String key, final String... member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.srem(key, member);
        }
    }

    public String spop(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.spop(key);
        }
    }

    public Set<String> spop(final String key, final long count) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.spop(key, count);
        }
    }

    public Long scard(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    public Boolean sismember(final String key, final String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.sismember(key, member);
        }
    }

    public String srandmember(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.srandmember(key);
        }
    }

    public List<String> srandmember(final String key, final int count) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.srandmember(key, count);
        }
    }

    public Long strlen(final String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.strlen(key);
        }
    }

    // sorted set操作

    public Long zadd(final String key, final double score, final String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    public Long zadd(final String key, final double score,
                     final String member, final ZAddParams params) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zadd(key, score, member, params);
        }
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zadd(key, scoreMembers, params);
        }
    }

    public Set<String> zrange(final String key, final long start, final long end) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    public Long zrem(final String key, final String... member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrem(key, member);
        }
    }

    public Double zincrby(final String key, final double score, final String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    public Double zincrby(final String key, final double score,
                          final String member, final ZIncrByParams params) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zincrby(key, score, member, params);
        }
    }

    public Long zrank(String key, String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrank(key, member);
        }
    }

    public Long zrevrank(String key, String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrevrank(key, member);
        }
    }

    public Set<String> zrevrange(String key, long start, long end) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zrevrangeWithScores(key, start, end);
        }
    }

    public Long zcard(String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zcard(key);
        }
    }

    public Double zscore(String key, String member) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.zscore(key, member);
        }
    }

    public List<String> sort(String key) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.sort(key);
        }
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        try (Jedis jedis = redisPool.getResource()) {
            return jedis.sort(key, sortingParameters);
        }
    }

}
