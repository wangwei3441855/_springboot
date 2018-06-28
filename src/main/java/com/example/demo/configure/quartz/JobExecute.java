package com.example.demo.configure.quartz;

import com.example.demo.configure.SpringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@DisallowConcurrentExecution
public class JobExecute implements Job {
    private static Logger log = LoggerFactory.getLogger(QuartzManager.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("JobExecute.execute start.....");
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String classPath = jobDataMap.getString("classPath");
        String methodName = jobDataMap.getString("methodName");
        try {
            Object quartzTask = SpringUtils.getBean(classPath);
            Method method = BeanUtils.findMethod(quartzTask.getClass(), methodName, new Class[]{});
            method.invoke(quartzTask, new Object[]{});
        } catch (IllegalAccessException e) {
            log.error("execute IllegalAccessException ", e);
        } catch (InvocationTargetException e) {
            log.error("execute InvocationTargetException ", e);
        } catch (Exception e) {
            log.error("execute Exception ", e);
        }
        log.info("JobExecute.execute end.....");
    }
}
