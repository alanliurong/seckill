package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.seckill.base.BaseSpringTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cheng on 2016/7/9.
 */
public class SeckillServiceTest extends BaseSpringTest {

    private static final Logger log = LoggerFactory.getLogger(SeckillServiceTest.class);

    @Autowired
    private SeckillService      seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        log.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        log.info("seckill={}", seckill);
    }

//    @Test
//    public void exportSeckillUrl() throws Exception {
//        long id = 1000;
//        Exposer exposer = seckillService.exportSeckillUrl(1000);
//        log.info("exposer={}", exposer);
//    }

    //    @Test
    //    public void executeSeckill() throws Exception {
    //        long id = 1000;
    //        long phone = 15686100226L;
    //        String md5 = "b443f12a1456f14a57471760088d6513";
    //        try {
    //            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
    //            log.info("resule={}", seckillExecution);
    //        } catch (RepeatKillException e) {
    //            log.error(e.getMessage());
    //        } catch (SeckillCloseException e) {
    //            log.error(e.getMessage());
    //        } catch (SeckillException e) {
    //            log.error(e.getMessage());
    //        }
    //
    //    }

    //测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(1000);
        if (exposer.isExposed()) {
            log.info("exposer={}", exposer);
            long phone = 15686100227L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                log.info("resule={}", seckillExecution);
            } catch (RepeatKillException e) {
                log.error(e.getMessage());
            } catch (SeckillCloseException e) {
                log.error(e.getMessage());
            } catch (SeckillException e) {
                log.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            log.info("exposer={}", exposer);
        }
    }

}