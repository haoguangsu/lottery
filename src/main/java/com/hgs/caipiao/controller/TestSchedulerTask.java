package com.hgs.caipiao.controller;

import com.hgs.caipiao.service.CaipiaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：206612
 * @date ：Created in 2021/9/29 15:02
 * @description：TestSchedulerTask
 */
@Component
public class TestSchedulerTask {

    @Autowired
    private CaipiaoServiceImpl service;

    @Scheduled(cron = "0 0 12 * * ?") // 每天中午12点触发
    public void startScanTask()
    {
        try {
            service.refreshHistory();
        } catch (Exception e) {
            throw e;
        }
    }
}
