package com.gcalendar.maker.exception.handler;

import com.gcalendar.maker.logger.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class ControllerExceptionHandler {

    @Autowired
    private Logger logger;

    @AfterThrowing(pointcut = "execution(* com.gcalendar.maker..* (..))", throwing = "throwable")
    public String handle (JoinPoint joinPoint, Throwable throwable) throws Throwable {
        Class<?> clazz = joinPoint.getTarget().getClass();
        logger.error(throwable, clazz);
        throw throwable;
    }

}
