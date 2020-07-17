package com.lening.tssk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 创作时间：2020-07-15 11:02
 * 作者：黄一帆
 */
@Component
public class MyTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    int number = 1;

    @Scheduled(cron = "0/5 * * * * ?")
    public void task(){
        logger.info("-------------第" + number + "次执行定时 任务");

        number++;
    }
}
