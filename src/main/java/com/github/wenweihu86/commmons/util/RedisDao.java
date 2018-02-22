package com.github.wenweihu86.commmons.util;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对redis集群模式的使用封装，基于jedis
 */
@Setter
@Getter
public class RedisDao {
    private static final Logger LOG = LoggerFactory.getLogger(RedisDao.class);

    // 集群节点集合
    private Set<HostAndPort> nodes;
    // 连接超时时间，单位毫秒
    private int connectionTimeoutMs = 500;
    // 读写超时时间，单位毫秒
    private int readWriteTimeoutMs = 1000;
    // 最大重试次数，包括首次请求
    private int maxRetryCount = 3;
    // 连接池配置，同apache commons pool配置
    private JedisPoolConfig redisPoolConfig = new JedisPoolConfig();
    // jedis cluster对象
    private JedisCluster redisCluster;

    /**
     * 初始化连接
     */
    public void init() {
        redisCluster = new JedisCluster(nodes, connectionTimeoutMs,
                readWriteTimeoutMs, maxRetryCount, redisPoolConfig);
        Map<String, JedisPool> clusterNodes = redisCluster.getClusterNodes();
        if (clusterNodes.keySet().size() == 0) {
            LOG.warn("redis cluster node size == 0");
            throw new RuntimeException("redis cluster node size == 0");
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (redisCluster != null) {
            try {
                redisCluster.close();
            } catch (IOException ex) {
                LOG.warn("close redis cluster exception={}", ex);
            }
        }
    }

    // 普通kv操作

    public String set(final String key, final String value) {
        return redisCluster.set(key, value);
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
        return redisCluster.set(key, value, nxxx, expx, time);
    }

    public String get(final String key) {
        return redisCluster.get(key);
    }

    public Long del(final String key) {
        return redisCluster.del(key);
    }

    // hash操作

    public Long hset(final String key, final String field, final String value) {
        return redisCluster.hset(key, field, value);
    }

    public String hget(final String key, final String field) {
        return redisCluster.hget(key, field);
    }

    public String hmset(final String key, final Map<String, String> hash) {
        return redisCluster.hmset(key, hash);
    }

    public List<String> hmget(final String key, final String... fields) {
        return redisCluster.hmget(key, fields);
    }

    public Map<String, String> hgetAll(final String key) {
        return redisCluster.hgetAll(key);
    }

    // list操作

    public Long lpush(final String key, final String... values) {
        return redisCluster.lpush(key, values);
    }

    public Long llen(final String key) {
        return redisCluster.llen(key);
    }

    public List<String> lrange(final String key, final long start, final long end) {
        return redisCluster.lrange(key, start, end);
    }

    public String lindex(final String key, final long index) {
        return redisCluster.lindex(key, index);
    }

    public String lset(final String key, final long index, final String value) {
        return redisCluster.lset(key, index, value);
    }

    public Long lrem(final String key, final long count, final String value) {
        return redisCluster.lrem(key, count, value);
    }

    public String lpop(final String key) {
        return redisCluster.lpop(key);
    }

    public String rpop(final String key) {
        return redisCluster.rpop(key);
    }

    // set操作

    public Long sadd(final String key, final String... member) {
        return redisCluster.sadd(key, member);
    }

    public Set<String> smembers(final String key) {
        return redisCluster.smembers(key);
    }

    public Long srem(final String key, final String... member) {
        return redisCluster.srem(key, member);
    }

    public String spop(final String key) {
        return redisCluster.spop(key);
    }

    public Set<String> spop(final String key, final long count) {
        return redisCluster.spop(key, count);
    }

    public Long scard(final String key) {
        return redisCluster.scard(key);
    }

    public Boolean sismember(final String key, final String member) {
        return redisCluster.sismember(key, member);
    }

    public String srandmember(final String key) {
        return redisCluster.srandmember(key);
    }

    public List<String> srandmember(final String key, final int count) {
        return redisCluster.srandmember(key, count);
    }

    public Long strlen(final String key) {
        return redisCluster.strlen(key);
    }

    // sorted set操作

    public Long zadd(final String key, final double score, final String member) {
        return redisCluster.zadd(key, score, member);
    }

    public Long zadd(final String key, final double score,
                     final String member, final ZAddParams params) {
        return redisCluster.zadd(key, score, member, params);
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return redisCluster.zadd(key, scoreMembers);
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params) {
        return redisCluster.zadd(key, scoreMembers, params);
    }

    public Set<String> zrange(final String key, final long start, final long end) {
        return redisCluster.zrange(key, start, end);
    }

    public Long zrem(final String key, final String... member) {
        return redisCluster.zrem(key, member);
    }

    public Double zincrby(final String key, final double score, final String member) {
        return redisCluster.zincrby(key, score, member);
    }

    public Double zincrby(final String key, final double score,
                          final String member, final ZIncrByParams params) {
        return redisCluster.zincrby(key, score, member, params);
    }

    public Long zrank(String key, String member) {
        return redisCluster.zrank(key, member);
    }

    public Long zrevrank(String key, String member) {
        return redisCluster.zrevrank(key, member);
    }

    public Set<String> zrevrange(String key, long start, long end) {
        return redisCluster.zrevrange(key, start, end);
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return redisCluster.zrangeWithScores(key, start, end);
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return redisCluster.zrevrangeWithScores(key, start, end);
    }

    public Long zcard(String key) {
        return redisCluster.zcard(key);
    }

    public Double zscore(String key, String member) {
        return redisCluster.zscore(key, member);
    }

    public List<String> sort(String key) {
        return redisCluster.sort(key);
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        return redisCluster.sort(key, sortingParameters);
    }

}
