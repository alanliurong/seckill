package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cheng
 * @version Id: SuccessKilled.java, v 0.1 2016/7/5 23:51 cheng Exp $$
 */
public class SuccessKilled implements Serializable{
    /**
     * 秒杀商品id
     */
    private long    seckillId;
    /**
     * 用户手机号
     */
    private long    userPhone;
    /**
     * 状态表示：-1:无效 0:成功 1:已付款 2:已发货
     */
    private short   state;
    /**
     * 创建时间
     */
    private Date    createDate;

    //变通
    //多对一
    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" + "seckillId=" + seckillId + ", userPhone=" + userPhone + ", state="
               + state + ", createDate=" + createDate + '}';
    }

}
