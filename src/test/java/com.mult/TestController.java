package com.mult;

import com.mult.basic.utils.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

public class TestController extends BaseJunit4Test{
    @Test
    public void redisTest(){
        JedisPool jedisPool = RedisUtil.getPool();
        jedisPool.getResource().set("test","test");
        String s  = jedisPool.getResource().get("test");
        System.err.println(s);
    }
}
