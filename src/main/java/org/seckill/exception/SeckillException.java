/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package org.seckill.exception;

/**
 * 秒杀相关业务异常
 *
 * @author cheng
 * @version Id: SeckillException.java, v 0.1 2016/7/9 16:10 cheng Exp $$
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
