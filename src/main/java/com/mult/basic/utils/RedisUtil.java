package com.mult.basic.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis工具类
 * @author AlexWalker
 * @date 2018/8/9 10:36
 */
@Slf4j
public class RedisUtil {

    /**Redis服务器IP*/
    private static String HOST= "127.0.0.1,127.0.0.1";
    /**Redis的端口*/
    private static int PORT = 20001;
    /**访问密码*/
    private static String AUTH = "123456";
    /**可用连接实例的最大数目，默认值为8；
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。*/
    private static int MAX_ACTIVE = 500;
    /**控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。*/
    private static int MAX_IDLE = 100;
    /**等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException;*/
    private static int MAX_WAIT = 10 * 1000;
    /**超时时间*/
    private static int TIMEOUT = 10 * 1000;
    /**在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的*/
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    public static synchronized Jedis getJedis() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            //使用时进行扫描，确保都可用
            config.setTestOnBorrow(TEST_ON_BORROW);
            //Idle时进行连接扫描
            config.setTestWhileIdle(true);
            //还回线程池时进行扫描
            config.setTestOnReturn(true);
////表示idle object evitor两次扫描之间要sleep的毫秒数
//            config.setTimeBetweenEvictionRunsMillis(30000);
////表示idle object evitor每次扫描的最多的对象数
//            config.setNumTestsPerEvictionRun(10);
////表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
//            config.setMinEvictableIdleTimeMillis(60000);

            if (StringUtils.isNotBlank(AUTH)) {
                jedisPool = new JedisPool(config, HOST.split(",")[0], PORT, TIMEOUT, AUTH);
            } else {
                jedisPool = new JedisPool(config, HOST.split(",")[0], PORT, TIMEOUT);
            }
        } catch (Exception e) {
            log.error("First create JedisPool error : " + e);
            try {
                //如果第一个IP异常，则访问第二个IP
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                jedisPool = new JedisPool(config, HOST.split(",")[1], PORT, TIMEOUT, AUTH);
            } catch (Exception e2) {
                log.error("Second create JedisPool error : " + e2);
            }
        }
        return jedisPool.getResource();
    }

    /**
     * 释放资源
     */
    public static void closePool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized static void setString(String key, String value) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().set(key, value);
        } catch (Exception e) {
            log.error("Set key error : " + e);
        }
    }

    /**
     * 设置 过期时间
     *
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public synchronized static void setString(String key, int seconds, String value) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().setex(key, seconds, value);
        } catch (Exception e) {
            log.error("Set keyex error : " + e);
        }
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public synchronized static String getString(String key) {
        if (getJedis() == null || !getJedis().exists(key)) {
            return null;
        }
        return getJedis().get(key);
    }

}