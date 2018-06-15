package com.example.demo.configure.quartz;

import com.example.demo.configure.BusinessException;
import com.example.demo.domain.QuartzJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 定时任务管理
 */
@Component
public class QuartzManager {

    private static Logger log = LoggerFactory.getLogger(QuartzManager.class);
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();


    public static void startAllJob() {
        log.info("QuartzManager.startAllJob start........");
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error("startAllJob SchedulerException: startAllJob fail", e);
            throw new BusinessException("startAllJob SchedulerException: startAllJob fail");
        }
        log.info("QuartzManager.startAllJob end........");
    }

    /**
     * 启动定时任务
     *
     * @param job
     */
    public static void addJob(QuartzJob job) {
        log.info("QuartzManager.addJob start........");
        if (job == null) {
            throw new BusinessException("addJob fail: QuartzJob is empty");
        }
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            String jobName = job.getName();
            String jobGroup = job.getGroup();
            String cron = job.getCron();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(JobExecute.class)
                    .withIdentity(jobName, jobGroup)
                    .usingJobData("classPath", job.getClassPath())
                    .usingJobData("methodName", job.getMethodName())
                    .build();

            //创建触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("addJob SchedulerException: addJob fail", e);
            throw new BusinessException("addJob SchedulerException: addJob fail");
        }
        log.info("QuartzManager.addJob end........");
    }

    /**
     * 暂停任务
     *
     * @param job
     */
    public static void pauseJob(QuartzJob job) {
        log.info("QuartzManager.pauseJob start........");
        if (job == null) {
            throw new BusinessException("pauseJob fail: QuartzJob is empty");
        }
        try {
            JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("pauseJob SchedulerException: pauseJob fail", e);
            throw new BusinessException("pauseJob SchedulerException: pauseJob fail");
        }
        log.info("QuartzManager.pauseJob end........");
    }

    /**
     * 恢复任务
     * @param job
     */
    public static void resumeJob(QuartzJob job){
        log.info("QuartzManager.resumeJob start........");
        if (job == null) {
            throw new BusinessException("resumeJob fail: QuartzJob is empty");
        }
        try {
            JobKey jobKey = JobKey.jobKey(job.getName(),job.getGroup());
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("resumeJob SchedulerException: resumeJob fail", e);
            throw new BusinessException("resumeJob SchedulerException: create fail");
        }
        log.info("QuartzManager.resumeJob end........");
    }
}
