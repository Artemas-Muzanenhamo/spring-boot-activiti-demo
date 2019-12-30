package com.activiti.demo.exception;

public class InvalidProcessInstanceKeyException extends RuntimeException {
    public InvalidProcessInstanceKeyException(String message) {
        super(message);
    }
}
