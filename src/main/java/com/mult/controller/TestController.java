package com.mult.controller;

import com.mult.basic.utils.RedisUtil;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.JedisPool;


@Controller
public class TestController {
    public static void main(String[] args) {
        JedisPool jedisPool = RedisUtil.getPool();
        jedisPool.getResource().set("test","test");
        String s  = jedisPool.getResource().get("test");
        System.err.println(s);
    }
}
