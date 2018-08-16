package com.mult.service;

import com.mult.basic.Result;

/**
 * @author Weirdo
 * @date 2018/8/15 13:55
 */
public interface ISeckillService {
    /**
     * 创建秒杀订单
     * @param sid 秒杀库存表id
     * @return
     */
    public Result createKillOrder(Integer sid);
}
