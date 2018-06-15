package com.example.demo.configure.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            //反射执行调度方法
            Class clazz = Class.forName(classPath);
            Method declaredMethod = clazz.getDeclaredMethod(methodName, new Class<?>[]{});
            declaredMethod.invoke(clazz.newInstance(), new Object[]{});
        } catch (ClassNotFoundException e) {
            log.error("JobExecute.execute ClassNotFoundException " + classPath);
        } catch (NoSuchMethodException e) {
            log.error("JobExecute.execute NoSuchMethodException " + methodName);
        } catch (IllegalAccessException e) {
            log.error("JobExecute.execute invoke IllegalAccessException " + methodName);
        } catch (InvocationTargetException e) {
            log.error("JobExecute.execute invoke InvocationTargetException" + methodName);
        } catch (InstantiationException e) {
            log.error("JobExecute.execute InstantiationException " + classPath);
        }
        log.info("JobExecute.execute end.....");
    }
}
