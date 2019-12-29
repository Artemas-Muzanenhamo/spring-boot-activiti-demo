package com.activiti.demo.exception;

public class InvalidProcessNameException extends RuntimeException {
    public InvalidProcessNameException(String message) {
        super(message);
    }
}
