package com.example.demo.service.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzTask {
    private Logger log = LoggerFactory.getLogger(QuartzTask.class);

    @Autowired
    protected QuartzService quartzService;

    public void test(){
        log.info("QuartzTask.test............");
        quartzService.test();
        log.info("QuartzTask.test............");
    }
}
