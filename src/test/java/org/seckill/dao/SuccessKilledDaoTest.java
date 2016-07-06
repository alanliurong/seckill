package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.seckill.entity.SuccessKilled;

/**
 * Created by cheng on 2016/7/6.
 */
public class SuccessKilledDaoTest extends BaseSpringTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 13502181181L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long phone = 13502181181L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println("successKilled = " + successKilled);
        System.out.println("Seckill = " + successKilled.getSeckill());
    }

}