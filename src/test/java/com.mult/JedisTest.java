package com.mult;


import com.mult.basic.utils.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest extends BaseJunit4Test{
    @Test
    public void redisTest(){
        Jedis jedis = RedisUtil.getJedis();
        String s  = jedis.get("watchkey");
        System.err.println(s);
    }
}
