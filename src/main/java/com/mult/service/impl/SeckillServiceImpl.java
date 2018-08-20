package com.mult.service.impl;

import com.mult.basic.Result;
import com.mult.basic.utils.RedisUtil;
import com.mult.dao.SeckillMapper;
import com.mult.model.Seckill;
import com.mult.model.SeckillExample;
import com.mult.service.ISeckillService;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import sun.rmi.runtime.Log;

import javax.transaction.Synchronization;
import java.util.Date;
import java.util.List;

/**
 * @author Weirdo
 * @date 2018/8/15 13:55
 */
@Service(value = "SeckillService")
public class SeckillServiceImpl implements ISeckillService {

    Logger log = LoggerFactory.getLogger(SeckillServiceImpl.class);
    @Autowired
    private SeckillMapper seckillMapper;

    final String watchkey = "watchkey";// 监视keys
    final int sku_num = 100; //总库存
    private long starttime; //秒杀开始时间
    private String userid = "admin";  //用户id
    boolean flag = true; //秒杀结束标识

    @Override
    public synchronized Result createKillOrder(Integer sid) {
        Result result = new Result();
        //todo
        //判断秒杀是否结束根据时间flag
        if (flag) {
            Jedis jedis = RedisUtil.getJedis();
            try {
                //一般是和事务一起使用，
                // 当对某个key进行watch后如果其他的客户端对这个key进行了更改，
                // 那么本次事务会被取消，事务的exec会返回null。jedis.watch(key)都会返回OK
                jedis.watch(watchkey);
                //获取库存redis
                int succ_count = Integer.valueOf(jedis.get(watchkey));
                //1.判断库存
                if (succ_count < sku_num) {
                    // 开启事务
                    Transaction tx = jedis.multi();
                    //2.扣减库存计算器,如果是空则从0开始,每次加1
                    tx.incr(watchkey);
                    // 提交事务，如果此时watchkey被改动了，则返回null
                    List<Object> list = tx.exec();
                    if (list != null) {
                        log.info("用户：{}抢购成功，当前抢购成功人数:{}", userid, (succ_count + 1));
                        //抢购成功业务逻辑
                        jedis.sadd("setsucc", userid);
                        //入库持久化
                        //todo

                        //成功
                        result.setStat(0);
                    } else {
                        //log.info("用户：{}抢购失败",userid);
                        //抢购失败业务逻辑
                        jedis.sadd("setfail", userid);
                    }
                } else {
                    //抢购结束，拒绝后续申请
                    //todo
                    jedis.sadd("setfail", userid);
                    result.setMsg("手速太慢,已无库存");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //System.out.println("总耗时：" + (new Date().getTime() - starttime));
            }
        }

        return result;
    }

}
