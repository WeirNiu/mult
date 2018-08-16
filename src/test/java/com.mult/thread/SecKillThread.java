package com.mult.thread;

import java.util.Date;
import java.util.List;

import com.mult.basic.utils.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class SecKillThread implements Runnable {
    final String watchkey = "watchkey";// 监视keys
    final int sku_num = 10; //总库存
    private long starttime; //秒杀开始时间
    private String userid;  //用户id
    private static boolean flag = true; //秒杀结束标识

    public SecKillThread(String userid, long starttime) {
        this.userid = userid;
        this.starttime = starttime;
    }

    public void run() {
        if (flag) {
            Jedis jedis = RedisUtil.getJedis();
            try {
                jedis.watch(watchkey);// watchkeys
                int succ_count = Integer.valueOf(jedis.get(watchkey));
                if (succ_count < sku_num) {
                    // 开启事务
                    Transaction tx = jedis.multi();
                    //计算器,如果是空则从0开始,每次加1
                    tx.incr(watchkey);
                    // 提交事务，如果此时watchkey被改动了，则返回null
                    List<Object> list = tx.exec();
                    if (list != null) {
                        System.out.println("用户：" + userid + "抢购成功，当前抢购成功人数:"
                                + (succ_count + 1));
                        //抢购成功业务逻辑
                        jedis.sadd("setsucc", userid);
                        //可直接入库持久化
                        //。。。。
                    } else {
                        System.out.println("用户：" + userid + "抢购失败");
                    /* 抢购失败业务逻辑 */
                        jedis.sadd("setfail", userid);
                    }
                } else {
                    //抢购结束，拒绝后续申请
                    flag = false;
                    //System.out.println("抢购结束");
                    jedis.sadd("setfail", userid);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("总耗时：" + (new Date().getTime() - starttime));
            }
        }
    }
}