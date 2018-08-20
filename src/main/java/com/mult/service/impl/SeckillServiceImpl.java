package com.mult.service.impl;

import com.mult.basic.Result;
import com.mult.basic.utils.RedisUtil;
import com.mult.dao.SeckillMapper;
import com.mult.service.ISeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author Weirdo
 * @date 2018/8/15 13:55
 */
@Service(value = "SeckillService")
@Slf4j
public class SeckillServiceImpl implements ISeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    /**监视key*/
    final private String watchKey = "watchKey";
    /**总库存*/
    final private Integer skuNum = 100;
    /**用户id*/
    private String userId = "admin";

    @Override
    public synchronized Result createKillOrder(Integer sid) {
        Result result = new Result();
        //todo
        //判断秒杀是否结束根据时间
        if (true) {
            Jedis jedis = RedisUtil.getJedis();
            try {
                //一般是和事务一起使用，
                // 当对某个key进行watch后如果其他的客户端对这个key进行了更改，
                // 那么本次事务会被取消，事务的exec会返回null。jedis.watch(key)都会返回OK
                jedis.watch(watchKey);
                //获取库存redis
                int succCount = Integer.valueOf(jedis.get(watchKey));
                //1.判断库存
                if (succCount < skuNum) {
                    // 开启事务
                    Transaction tx = jedis.multi();
                    //2.扣减库存计算器,如果是空则从0开始,每次加1
                    tx.incr(watchKey);
                    // 提交事务，如果此时watchkey被改动了，则返回null
                    List<Object> list = tx.exec();
                    if (list != null) {
                        log.info("用户：{}抢购成功，当前抢购成功人数:{}", userId, (succCount + 1));
                        //抢购成功业务逻辑
                        jedis.sadd("setSucc", userId);
                        //入库持久化
                        //todo

                        //成功
                        result.setStat(0);
                    } else {
                        //log.info("用户：{}抢购失败",userid);
                        //抢购失败业务逻辑
                        jedis.sadd("setFail", userId);
                    }
                } else {
                    //抢购结束，拒绝后续申请
                    //todo
                    jedis.sadd("setFail", userId);
                    result.setMsg("手速太慢,已无库存");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
