package com.deenn.datawarehouse.exception;

public class DealNotFoundException extends RuntimeException {

    private String message;

    public DealNotFoundException() {
        super();
        this.message = "Deal not found";
    }

    public DealNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
