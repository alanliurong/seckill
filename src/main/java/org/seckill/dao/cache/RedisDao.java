/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author cheng
 * @version Id: RedisDao.java, v 0.1 2016/7/12 22:45 cheng Exp $$
 */
public class RedisDao {
    private static final Logger    log    = LoggerFactory.getLogger(RedisDao.class);

    private JedisPool              jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    //获取缓存
    public Seckill getSeckill(long seckillId) {
        //redis 操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill" + seckillId;
                //get -> byte[] -> 反序列化 -> Object(Seckill)
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    Seckill seckill = schema.newMessage();//空对象
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    //Seckill被反序列化
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    //添加缓存
    public String putSeckill(Seckill seckill) {
        //set Object(Seckill) -> 序列化 -> byte[] -> 发送给redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill" + seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                int timeout = 60 * 60;//超时缓存
                String result = jedis.setex(key.getBytes(), timeout, bytes);

                return result;

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean acquireLock(String lock) {
        Jedis jedis = jedisPool.getResource();
        boolean success = false;
        long value = System.currentTimeMillis() + 2 * 60 * 1000 + 1;
        log.info("设置时间={}" + value);
        long acquire = jedis.setnx(lock.getBytes(), String.valueOf(value).getBytes());

        if (acquire == 1) {
            success = true;
        } else {
            long oldValue = Long.valueOf(jedis.get(lock));
            //如果设置的时间小于当前时间说明超时
            if (oldValue < System.currentTimeMillis()) {
                //获取lock的值，并设置新值
                String getValue = jedis.getSet(lock, String.valueOf(value));

                //如果oldValue和getValue相等说明锁没有被占用
                if (Long.valueOf(getValue) == oldValue) {
                    //获取锁成功
                    success = true;
                } else {
                    //已经被获取
                    success = false;
                }
            } else {
                //未超时
                success = false;
            }
        }
        return success;
    }

    public void releaseLock(String lock) {
        Jedis jedis = jedisPool.getResource();
        long current = System.currentTimeMillis();
        if (current < Long.valueOf(jedis.get(lock))) {
            jedis.del(lock);
        }
    }
}
