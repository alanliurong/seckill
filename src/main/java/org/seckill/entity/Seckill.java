/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.entity;

import java.util.Date;

/**
 * @author cheng
 * @version Id: Seckill.java, v 0.1 2016/7/5 23:43 cheng Exp $$
 */
public class Seckill {
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
    private Date   startDate;
    /**
     *秒杀结束时间
     */
    private Date   endDate;
    /**
     * 创建时间
     */
    private Date   createDate;

    public Seckill() {
    }

    public Seckill(long seckillId, String name, int number, Date endDate, Date startDate,
                   Date createDate) {
        this.seckillId = seckillId;
        this.name = name;
        this.number = number;
        this.endDate = endDate;
        this.startDate = startDate;
        this.createDate = createDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
               + number + ", startDate=" + startDate + ", endDate=" + endDate + ", createDate="
               + createDate + '}';
    }
}
