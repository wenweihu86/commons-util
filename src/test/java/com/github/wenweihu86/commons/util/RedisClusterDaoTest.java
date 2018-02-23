package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.RedisClusterDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class RedisClusterDaoTest {
    private RedisClusterDao redisDao;

    @Before
    public void setUp() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 30000));
        nodes.add(new HostAndPort("127.0.0.1", 30001));
        nodes.add(new HostAndPort("127.0.0.1", 30002));
        nodes.add(new HostAndPort("127.0.0.1", 30003));
        nodes.add(new HostAndPort("127.0.0.1", 30004));
        nodes.add(new HostAndPort("127.0.0.1", 30005));
        redisDao = new RedisClusterDao();
        redisDao.setNodes(nodes);
        redisDao.init();
    }

    @After
    public void tearDown() {
        redisDao.close();
    }

    @Test
    public void testSetGet() {
        redisDao.set("hello", "world");
        Assert.assertTrue("world".equals(redisDao.get("hello")));
    }
}
