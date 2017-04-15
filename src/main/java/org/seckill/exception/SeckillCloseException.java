package org.seckill.exception;

/**
 * 秒杀关闭异常
 *
 * @author cheng
 * @version Id: SeckillCloseException.java, v 0.1 2016/7/9 16:08 cheng Exp $$
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
