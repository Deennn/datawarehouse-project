package com.deenn.datawarehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="activity_log")
public class ActivityLog extends BaseModel implements Serializable {

    @Column(columnDefinition="TEXT")
    private String requestBody;

    @Column(columnDefinition="TEXT")
    private String responseBody;

    private String responseStatus;

    private String requestMethod;

    private String requestMethodName;

    private String requestUrl;

    @Column(columnDefinition="TEXT")
    private String requestHeaders;

    private String responseHeaders;

    private String device;

    @Column(columnDefinition = "bit default 0")
    private boolean deleted;
}
