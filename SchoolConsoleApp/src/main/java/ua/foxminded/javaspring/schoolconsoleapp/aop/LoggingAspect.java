package ua.foxminded.javaspring.schoolconsoleapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    
    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.*)")
    public void processingMethods() {

    }
    
    @Around("processingMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.debug("Method: {} executed in {} milliseconds", joinPoint.getSignature(), executionTime);
        return proceed;
    }
    
    @AfterThrowing(pointcut = "processingMethods()", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        log.error("{}:", joinPoint.getSignature(), error);
    }
}
