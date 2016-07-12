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
}
