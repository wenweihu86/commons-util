package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.RedisDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedisDaoTest {
    private RedisDao redisDao;

    @Before
    public void setUp() {
        redisDao = new RedisDao();
        redisDao.setHost("127.0.0.1");
        redisDao.setPort(6379);
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
