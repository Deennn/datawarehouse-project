package com.deenn.datawarehouse.exception;

public class InternalServerErrorException extends RuntimeException {

    private String message;

    public InternalServerErrorException() {
        super();
        this.message = "Internal Server Error";
    }

    public InternalServerErrorException(String msg) {
        super(msg);
        this.message = msg;
    }
}
