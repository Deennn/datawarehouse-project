package com.deenn.datawarehouse.activityLog.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAdvice {


    @Around("execution(* com.deenn.datawarehouse.service.*.*.*(..))")
    public Object applicationLogger (ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] request = proceedingJoinPoint.getArgs();
        HttpServletRequest servletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = servletRequest.getRequestURL().toString();
        log.info("URL : " + url);
        log.info("Method invoked : " + methodName + "()");
        if (request.length == 2) log.info("Request : " + request[1].toString());
        if (request.length > 2) {
            Object[] subarray = Arrays.copyOfRange(request, 3, request.length);
            log.info("Request : " + Arrays.toString(subarray));
        }
        Object response = proceedingJoinPoint.proceed();
        log.info("URL : " + url +" : Method invoked : " + methodName + "() " + "Response : " + response);
        return  response;
    }
}
