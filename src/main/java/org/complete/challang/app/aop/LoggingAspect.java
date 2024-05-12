package org.complete.challang.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* org.complete.challang.app.*..controller.*.*(..)) ||" +
            "execution(* org.complete.challang.app.*..service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getSignature().getDeclaringTypeName();

        log.info("[START] {}.{}() =>", type, joinPoint.getSignature().getName());

        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.info("parameter{} : {} = {}", i, parameterNames[i], args[i]);
        }

        long startAt = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endAt = System.currentTimeMillis();

        log.info("<= [END]({}ms) {}.{}()", endAt - startAt, type, joinPoint.getSignature().getName());

        return proceed;
    }
}
