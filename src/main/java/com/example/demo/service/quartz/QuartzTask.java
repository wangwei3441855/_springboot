package com.example.demo.service.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzTask {
    private Logger log = LoggerFactory.getLogger(QuartzTask.class);

    public void test(){
        log.info("QuartzTask.test............");
        log.info("QuartzTask.test............");
        log.info("QuartzTask.test............");
        log.info("QuartzTask.test............");
        log.info("QuartzTask.test............");
    }

    public void test1(){
        log.info("QuartzTask.test1............");
        log.info("QuartzTask.test1............");
        log.info("QuartzTask.test1............");
        log.info("QuartzTask.test1............");
        log.info("QuartzTask.test1............");
    }
}
