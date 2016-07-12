/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author cheng
 * @version Id: SeckillServiceImpl.java, v 0.1 2016/7/9 16:16 cheng Exp $$
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private static final Logger log  = LoggerFactory.getLogger(SeckillServiceImpl.class);
    @Autowired
    private SeckillDao          seckillDao;
    @Autowired
    private SuccessKilledDao    successKilledDao;
    @Autowired
    private RedisDao            redisDao;

    //md5颜值字符串，用于混淆
    private final String        slat = "asdfghjkl20160709!@#$%^&*()_+";

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：缓存优化 //TODO
        //1：访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //2：访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                //3：放入redis
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(),
                endTime.getTime());
        }
        //转化特定字符转的过程，不可逆
        String md5 = getMD5(seckillId);//TODO md5
        return new Exposer(true, md5, seckillId);
    }


    /**
     * 使用注解控制事务方法的优点
     * 1：开发团队达成一致约定，明确 标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能的短，不要穿插其他网络操作RPC/HTTP请求或剥离到事务方法外部
     * 3：不是所有方法都需要事务，如只有一条修改操作，只读不需要事务
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone,
                                           String md5) throws SeckillException, RepeatKillException,
                                                       SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑：减库存+记录购买行为
        Date killTime = new Date();
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //唯一：seckillId,userPhone
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存
                int updateCount = seckillDao.reduceNumber(seckillId, killTime);
                if (updateCount <= 0) {
                    //没有更新到记录,秒杀结束 rollback
                    throw new SeckillCloseException("seckill is close");
                } else {
                    //秒杀成功 commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,
                        userPhone);
                    return new SeckillExecution(seckillId, successKilled, SeckillStatEnum.SUCCESS);
                }

            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //所有编译期异常，转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
