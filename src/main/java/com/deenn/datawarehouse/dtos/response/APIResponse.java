package com.deenn.datawarehouse.dtos.response;

import lombok.*;


@Getter @Setter
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private boolean success;
    private T payload;
}