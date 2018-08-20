package com.mult.thread;

import com.mult.basic.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * 并发测试类
 * @author Weirdo
 * @date 2018/8/16 9:39
 */
public class HighConcurrencyTest {
    public static void main(String[] args) {
        //重置库存
        Jedis jedis = RedisUtil.getJedis();
        jedis.set("watchkey","0");
        //并发数
        int count=300;
        //CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。
        final CountDownLatch cdl=new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cdl.countDown();
                    try {
                        cdl.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        ConnectionUtil.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
