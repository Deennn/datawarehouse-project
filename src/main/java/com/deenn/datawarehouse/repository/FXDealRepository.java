package com.deenn.datawarehouse.repository;

import com.deenn.datawarehouse.entity.FXDeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FXDealRepository extends JpaRepository<FXDeal, Long> {

    Page<FXDeal> findAll(Pageable pageable);

    @Query("SELECT fx FROM FXDeal fx WHERE fx.dealUniqueId = :id AND fx.deleted = false")
    Optional<FXDeal> findByDealUniqueId(@Param("id") String id);
}
