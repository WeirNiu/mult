package com.mult.controller;

import com.mult.basic.Result;
import com.mult.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author Weirdo
 * 秒杀请求控制器
 *
 */
@Controller
@RequestMapping("order")
public class SeckillController {
    private final static Logger logger = LoggerFactory.getLogger(SeckillController.class);
    @Resource(name = "SeckillService")
    private ISeckillService seckillService;

    @RequestMapping("createOrder/{sid}")
    @ResponseBody
    public Result createWrongOrder(@PathVariable Integer sid) {
        logger.info("sid=[{}]", sid);
        Result result = new Result();
        try {
            result = seckillService.createKillOrder(sid);
        } catch (Exception e) {
            logger.error("Exception",e);
        }
        return result;
    }
}
