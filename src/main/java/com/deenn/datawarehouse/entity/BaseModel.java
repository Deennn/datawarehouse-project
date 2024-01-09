package com.deenn.datawarehouse.entity;

import com.deenn.datawarehouse.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return DateUtil.getZonedLocalDateTime(createdAt);
    }

    public LocalDateTime getUpdatedAt() {
        return DateUtil.getZonedLocalDateTime(updatedAt);
    }
}

