package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

import java.io.Serializable;

/**
 * 封装秒杀执行后结果
 *
 * @author cheng
 * @version Id: SeckillExecution.java, v 0.1 2016/7/9 15:59 cheng Exp $$
 */
public class SeckillExecution implements Serializable{

    private long          seckillId;

    //秒杀执行结果状态
    private int           state;

    //状态表示
    private String        stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SuccessKilled successKilled, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.successKilled = successKilled;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" + "seckillId=" + seckillId + ", state=" + state + ", stateInfo='"
               + stateInfo + '\'' + ", successKilled=" + successKilled + '}';
    }
}
