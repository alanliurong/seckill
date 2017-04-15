package org.seckill.facade.impl;

import org.seckill.facade.SeckillFacade;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 * @version Id: SeckillService.java, v 0.1 2016/9/12 15:41 cheng Exp $$
 */
@Service
public class SeckillService implements SeckillFacade {

    public String testdubbo() {

        String test = "dubbo成功";

        return test;
    }
}
