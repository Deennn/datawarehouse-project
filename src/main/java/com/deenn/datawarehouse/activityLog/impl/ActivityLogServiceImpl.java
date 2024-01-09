package com.deenn.datawarehouse.activityLog.impl;

import com.deenn.datawarehouse.activityLog.ActivityLogService;
import com.deenn.datawarehouse.entity.ActivityLog;
import com.deenn.datawarehouse.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {


    private final ActivityLogRepository repository;

    @Override
    public void save(ActivityLog activityLog) {
        repository.save(activityLog);
    }

}
