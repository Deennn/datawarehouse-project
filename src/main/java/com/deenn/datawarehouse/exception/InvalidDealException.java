package com.deenn.datawarehouse.exception;

public class InvalidDealException extends RuntimeException{

    private String message;

    public InvalidDealException() {
        super();
        this.message = "Drone has reached it weight limit";
    }

    public InvalidDealException(String msg) {
        super(msg);
        this.message = msg;
    }
}
