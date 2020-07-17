package com.lening.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 创作时间：2020-07-14 16:02
 * 作者：黄一帆
 */
@Aspect
@Component
public class LogAop {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Pointcut(value = "execution(public * com.lening.service.TeacherService.*(..))")
    public void log() {

    }
    @Before(value = "log()")
    public void before(){
        logger.info("--------------前置通知----------------------------");
    }

    @After(value = "log()")
    public void after(){
        logger.info("--------------后置通知----------------------------");
    }

    @Around(value = "log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("------------拦截之前通知------------");
        Object proceed = proceedingJoinPoint.proceed();
        logger.info("------------拦截之后通知------------");

        return proceed;
    }
}
