package ua.foxminded.javaspring.schoolconsoleapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    private static final String FORMAT = "Method: {} executed in {} milliseconds";

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoJDBC)")
    public void CourseDaoJDBC_ProcessingMethods() {

    }

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoJDBC)")
    public void GroupDaoJDBC_ProcessingMethods() {

    }

    @Pointcut("within(ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDaoJDBC)")
    public void StudentDaoJDBC_ProcessingMethods() {

    }

    @Around("CourseDaoJDBC_ProcessingMethods()")
    public Object logExecutionTime_CourseDaoJDBC(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(FORMAT, joinPoint.getSignature(), executionTime);
            return proceed;

        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new IllegalStateException(e);
        }
    }

    @Around("GroupDaoJDBC_ProcessingMethods()")
    public Object logExecutionTime_GroupDaoJDBC(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(FORMAT, joinPoint.getSignature(), executionTime);
            return proceed;

        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new IllegalStateException(e);
        }
    }

    @Around("StudentDaoJDBC_ProcessingMethods()")
    public Object logExecutionTime_StudentDaoJDBC(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(FORMAT, joinPoint.getSignature(), executionTime);
            return proceed;

        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new IllegalStateException(e);
        }
    }
}
