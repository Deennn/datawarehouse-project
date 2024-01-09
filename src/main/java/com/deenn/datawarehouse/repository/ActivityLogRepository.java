package com.deenn.datawarehouse.repository;

import com.deenn.datawarehouse.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
