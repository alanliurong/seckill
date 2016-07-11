/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cheng
 * @version Id: Seckill.java, v 0.1 2016/7/5 23:43 cheng Exp $$
 */
public class Seckill implements Serializable{
    /**
     * 秒杀ID
     */
    private long   seckillId;
    /**
     * 商品名
     */
    private String name;
    /**
     * 库存数量
     */
    private int    number;
    /**
     *秒杀开始时间
     */
    private Date   startTime;
    /**
     *秒杀结束时间
     */
    private Date   endTime;
    /**
     * 创建时间
     */
    private Date   createTime;

    public Seckill() {
    }

    public Seckill(long seckillId, String name, int number, Date endTime, Date startTime,
                   Date createTime) {
        this.seckillId = seckillId;
        this.name = name;
        this.number = number;
        this.endTime = endTime;
        this.startTime = startTime;
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endDate) {
        this.endTime = endDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    @Override
    public String toString() {
        return "Seckill{" + "seckillId=" + seckillId + ", name='" + name + '\'' + ", number="
               + number + ", startDate=" + startTime + ", endDate=" + endTime + ", createDate="
               + createTime + '}';
    }
}
