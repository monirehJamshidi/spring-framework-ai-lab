package org.j2os.common.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("execution(* org.j2os.account.service..*(..))")
    public Object logServiceExecution(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        String method =
                joinPoint.getSignature().toShortString();

        long start = System.nanoTime();

        log.debug("Starting {}", method);

        try {
            Object result = joinPoint.proceed();

            long durationMs =
                    (System.nanoTime() - start) / 1_000_000;

            log.debug(
                    "Completed {} in {} ms",
                    method,
                    durationMs
            );

            return result;
        } catch (Throwable ex) {

            long durationMs =
                    (System.nanoTime() - start) / 1_000_000;

            log.error(
                    "Failed {} after {} ms",
                    method,
                    durationMs,
                    ex
            );

            throw ex;
        }


    }
}
