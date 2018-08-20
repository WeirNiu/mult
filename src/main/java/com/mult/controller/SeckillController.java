package com.mult.controller;

import com.mult.basic.Result;
import com.mult.service.ISeckillService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SeckillController {
    @Resource(name = "SeckillService")
    private ISeckillService seckillService;

    @RequestMapping("createSeckillOrder/{sid}")
    @ResponseBody
    public Result createWrongOrder(@PathVariable Integer sid) {
        log.info("sid=[{}]", sid);
        Result result = new Result();
        try {
            result = seckillService.createKillOrder(sid);
        } catch (Exception e) {
            log.error("Exception",e);
        }
        return result;
    }
}
