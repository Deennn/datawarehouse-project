package com.deenn.datawarehouse.activityLog.advice;

import com.deenn.datawarehouse.activityLog.ActivityLogService;
import com.deenn.datawarehouse.entity.ActivityLog;
import com.deenn.datawarehouse.utils.FXValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditTrail {


    private final ActivityLogService activityLogService;


    private final HttpServletRequest request;


    private final HttpServletResponse response;

    private final FXValidator app;




    @Around("execution(* com.deenn.datawarehouse.service.*.*.*(..))")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        ActivityLog auditLog = new ActivityLog();
        String methodName = joinPoint.getSignature().getName();
        String url = request.getRequestURL().toString();
        String requestMethod = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        String requestHeaders = getRequestHeaders();
        Object[] requestObj = joinPoint.getArgs();
        Object responseObj = joinPoint.proceed();
        String responseStatus = String.valueOf(response.getStatus());
        String responseHeaders = getResponseHeaders();
        String responseBody = responseObj != null ? responseObj.toString() : "";
        auditLog.setDevice(userAgent);
        auditLog.setRequestMethodName(methodName);
        auditLog.setRequestMethod(requestMethod);
        auditLog.setRequestUrl(url);
        auditLog.setRequestHeaders(requestHeaders);
        auditLog.setRequestBody(Arrays.toString(requestObj));
        auditLog.setResponseStatus(responseStatus);
        auditLog.setResponseHeaders(responseHeaders);
        auditLog.setResponseBody(responseBody);
        activityLogService.save(auditLog);

        return responseObj;
    }

    private String getRequestHeaders() {
        return Collections.list(request.getHeaderNames())
                .stream()
                .map(headerName -> headerName + ": " + request.getHeader(headerName))
                .collect(Collectors.joining("\n"));
    }


    private String getResponseHeaders() {
        return response.getHeaderNames()
                .stream()
                .map(headerName -> headerName + ": " + response.getHeader(headerName))
                .collect(Collectors.joining("\n"));
    }


}
