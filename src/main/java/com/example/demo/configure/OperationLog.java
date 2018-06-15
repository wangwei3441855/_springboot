package com.example.demo.configure;

import com.example.demo.controller.SysController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class OperationLog {
    private Logger log = LoggerFactory.getLogger(OperationLog.class);

    /**
     * 切入点com.example.demo.controller下的所有类，所有方法
     */
    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void controllerPointcut() {
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "returnObj")
    public void afterReturning(JoinPoint joinPoint, Object returnObj) {
        log.info("OperationLog.start.....");
        log.info("OperationLog.afterReturning.returnObj:{}", returnObj);
    }
}
