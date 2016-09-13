/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.util;

import org.seckill.facade.SeckillFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cheng
 * @version Id: DubboTest.java, v 0.1 2016/9/12 15:55 cheng Exp $$
 */
public class DubboTest {

    @Autowired
    private SeckillFacade seckillFacade;

    public void test(){
        System.out.println("qweqwe"+seckillFacade.testdubbo());
    }
}
