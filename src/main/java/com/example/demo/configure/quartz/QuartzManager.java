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

            //启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error("addJob SchedulerException: create Scheduler fail");
            throw new BusinessException("addJob SchedulerException: create Scheduler fail");
        }
        log.info("QuartzManager.addJob end........");
    }

    
    /**
     * 停止执行
     * @param job
     */
    public void stopJob(QuartzJob job){

    }
}
