package com.example.demo.service.quartz;

import com.example.demo.configure.quartz.QuartzManager;
import com.example.demo.domain.QuartzJob;
import com.example.demo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements QuartzService {

    private static Logger log = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Override
    public void addQuartzJob(QuartzJob job) {

    }

    @Override
    public void startQuartzJob() {
        log.info("QuartzService.startQuartzJob start....");

        QuartzJob job = new QuartzJob();
        job.setId(Utils.createUUid());
        job.setName("test");
        job.setGroup("group");
        job.setStatus("0");
        job.setCron("0 0/30 * * * ? *");
        job.setClassPath("com.example.demo.service.quartz.QuartzTask");
        job.setMethodName("test");
        QuartzManager.addJob(job);

        log.info("QuartzService.startQuartzJob end....");
    }
}
