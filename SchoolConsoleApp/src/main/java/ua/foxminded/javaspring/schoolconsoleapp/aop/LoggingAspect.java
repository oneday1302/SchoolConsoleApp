package ua.foxminded.javaspring.schoolconsoleapp.aop;

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

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoJDBC)")
    public void CourseDaoJDBC_ProcessingMethods() {

    }

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoJDBC)")
    public void GroupDaoJDBC_ProcessingMethods() {

    }

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDaoJDBC)")
    public void StudentDaoJDBC_ProcessingMethods() {

    }

    @Around("CourseDaoJDBC_ProcessingMethods() || GroupDaoJDBC_ProcessingMethods() || StudentDaoJDBC_ProcessingMethods()")
    public Object logExecutionTime_CourseDaoJDBC(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.debug("Method: {} executed in {} milliseconds", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    @AfterThrowing(pointcut = "CourseDaoJDBC_ProcessingMethods() || GroupDaoJDBC_ProcessingMethods() || StudentDaoJDBC_ProcessingMethods()", throwing = "error")
    public void afterThrowingAdvice(Throwable error) {
        log.error("Exception: ", error);
    }
}
