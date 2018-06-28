package com.example.demo.configure.quartz;

import com.example.demo.controller.WebSocketController;
import com.example.demo.domain.QuartzJob;
import com.example.demo.service.quartz.QuartzTask;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    private Logger log = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    private SpringJobFactory springJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactory(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        /*用于Quartz集群,启动时更新已存在的Job*/
        factoryBean.setOverwriteExistingJobs(true);
        /*定时任务开始启动后延迟5秒开始*/
        factoryBean.setStartupDelay(5);
        factoryBean.setJobFactory(springJobFactory);
        return factoryBean;
    }

    /**
     * 创建调度器
     * @return Scheduler
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactory().getScheduler();
    }

    /**
     * job的执行类
     * @return QuartzTask
     */
    @Bean(name="quartzTask")
    public QuartzTask getQuartzTask(){
        return new QuartzTask();
    }

}
