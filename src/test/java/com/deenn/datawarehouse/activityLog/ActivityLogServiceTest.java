package com.deenn.datawarehouse.activityLog;

import com.deenn.datawarehouse.activityLog.impl.ActivityLogServiceImpl;
import com.deenn.datawarehouse.entity.ActivityLog;
import com.deenn.datawarehouse.repository.ActivityLogRepository;
import com.deenn.datawarehouse.repository.FXDealRepository;
import com.deenn.datawarehouse.service.impl.FXDealServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ActivityLogServiceTest {

    private ActivityLogRepository repository;

    private ActivityLogServiceImpl service;

    @BeforeEach
    void setUp() {

        repository  = Mockito.mock(ActivityLogRepository.class);

        service = new ActivityLogServiceImpl(repository);
    }

    @AfterEach
    void tearDown() {


    }

    @Test
    void save() {

        ActivityLog activityLog = createSampleActivityLog();

        service.save(activityLog);

        verify(repository).save(activityLog);

        verify(repository, times(1)).save(activityLog);
    }

    private ActivityLog createSampleActivityLog() {
        ActivityLog auditLog = new ActivityLog();
        auditLog.setDevice("test");
        auditLog.setRequestMethodName("test");
        auditLog.setRequestMethod("test");
        auditLog.setRequestUrl("test");
        auditLog.setRequestHeaders("");
        auditLog.setRequestBody(Arrays.toString(new String[]{"uu"}));
        auditLog.setResponseStatus("test");
        auditLog.setResponseHeaders("tes");
        auditLog.setResponseBody("test");
      return auditLog;
    }
}