package org.seckill.util;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 * @version Id: MyElasticJob.java, v 0.1 2016/8/31 16:21 cheng Exp $$
 */
@Slf4j
public class MyElasticJob extends AbstractSimpleElasticJob{

    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        log.info("定时任务开始");
        try {
            Thread.sleep(2000);
            log.info("定时任务结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
