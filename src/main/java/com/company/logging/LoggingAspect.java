package com.company.logging;

import com.company.config.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Arrays;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final Environment env;

    @Pointcut("within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // for aop
    }

    @Pointcut("within(com.company.controller.*)" +
            " || within(com.company.service.*)")
    public void applicationPackagePointcut() {
        // for aop
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && " +
            "springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(
                ApplicationConstants.SPRING_PROFILE_LOCAL,
                ApplicationConstants.SPRING_PROFILE_DEV))) {
            log.error("Exception in {}.{}() with cause = {} and exception = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    getExceptionCause(e), e.getMessage(), e);
        } else {
            log.error("Exception in {}.{}() with cause = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), getExceptionCause(e));
        }
    }

    private Object getExceptionCause(Throwable throwable) {
        return throwable.getCause() != null ? throwable.getCause() : "NULL";
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}",
                    joinPoint.getSignature().getDeclaringTypeName(), // class name
                    joinPoint.getSignature().getName(), // method name
                    Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }

}