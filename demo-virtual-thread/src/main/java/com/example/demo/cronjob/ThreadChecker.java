package com.example.demo.cronjob;

import com.example.demo.util.ThreadUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Rizki Abdillah Azmi on 23-Sep-23
 */
@Component
@EnableScheduling
public class ThreadChecker {

    @Scheduled(cron = "0/10 * * ? * *")
    public void checkThread() {
        ThreadUtil.printThreads();
    }

}
