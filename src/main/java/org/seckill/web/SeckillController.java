/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author cheng
 * @version Id: SeckillController.java, v 0.1 2016/7/10 11:09 cheng Exp $$
 */
@Controller
@RequestMapping(value = "/seckill") //url:/模块/资源/{id}/细分
public class SeckillController {
    private Logger         log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";//=WEB-INFO/jsp/lsit.jsp
    }

    /**
     * 秒杀详情页
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    /**
     * 是否暴露秒杀地址
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀接口
     * @param seckillId
     * @param phone
     * @param md5
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "killPhone", required = false) Long phone,
                                                   @PathVariable("md5") String md5) {
        //可以使用 spingMVC valid
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            log.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId,
                SeckillStatEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillCloseException e) {
            log.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            result = new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillException e) {
            log.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId,
                SeckillStatEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false, execution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET, produces = { "application/json;charset=utf-8" })
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }

}
