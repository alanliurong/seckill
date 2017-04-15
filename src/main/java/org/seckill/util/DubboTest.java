package org.seckill.util;

import org.seckill.facade.SeckillFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cheng
 * @version Id: DubboTest.java, v 0.1 2016/9/12 15:55 cheng Exp $$
 */
public class DubboTest {

    @Autowired
    private SeckillFacade seckillService;

    public void test(){
        System.out.println("qweqwe"+seckillService.testdubbo());
    }

    public static void main(String[] args) {
        new DubboTest().test();
    }
}
