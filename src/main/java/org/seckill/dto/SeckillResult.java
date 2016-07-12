/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.dto;

import java.io.Serializable;

/**
 *  所有ajax请求返回的数据 封装json结果
 * @author cheng
 * @version Id: SeckillResult.java, v 0.1 2016/7/10 11:30 cheng Exp $$
 */
public class SeckillResult<T> implements Serializable{

    private boolean success;

    private T       date;

    private String  error;

    public SeckillResult(boolean success, T date) {
        this.success = success;
        this.date = date;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
