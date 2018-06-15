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
        job.setCron("0/50 * * * * ? ");
        job.setClassPath("com.example.demo.service.quartz.QuartzTask");
        job.setMethodName("test");
        QuartzManager.addJob(job);

        QuartzJob job1 = new QuartzJob();
        job1.setId(Utils.createUUid());
        job1.setName("test1");
        job1.setGroup("group");
        job1.setStatus("0");
        job1.setCron("0/20 * * * * ? ");
        job1.setClassPath("com.example.demo.service.quartz.QuartzTask");
        job1.setMethodName("test1");
        QuartzManager.addJob(job1);



        //启动所有任务
        QuartzManager.startAllJob();
        log.info("QuartzService.startQuartzJob end....");
    }

    @Override
    public void pauseJob() {
        QuartzJob job1 = new QuartzJob();
        job1.setName("test1");
        job1.setGroup("group");
        QuartzManager.pauseJob(job1);
    }

    @Override
    public void resumeJob() {
        QuartzJob job1 = new QuartzJob();
        job1.setName("test1");
        job1.setGroup("group");
        QuartzManager.resumeJob(job1);
    }
}
