package org.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 *
 * @author cheng
 * @version Id: RepeatKillException.java, v 0.1 2016/7/9 16:05 cheng Exp $$
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
