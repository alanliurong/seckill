package org.seckill.dao.cache;

import org.junit.Test;
import org.seckill.base.BaseSpringTest;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cheng on 2016/7/12.
 */
public class RedisDaoTest extends BaseSpringTest{

    private long id = 1001;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            seckill = seckillDao.queryById(id);
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println("resilt="+result);
                seckill = redisDao.getSeckill(id);
                System.out.println("seckill="+seckill);
            }
        }
        System.out.println("seckill2="+seckill);
    }

}