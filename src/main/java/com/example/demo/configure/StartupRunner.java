package com.example.demo.configure;

import com.example.demo.service.quartz.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartupRunner implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    protected QuartzService quartzService;

    @Override
    public void run(String... args) throws Exception {
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<StartupRunner.run>>>>>>>>>>>>>>>>>>>>>");
        try {
            quartzService.startQuartzJob();
        } catch (Exception e) {
            log.error("quartzService.startQuartzJob() fail", e);
        }
    }
}
