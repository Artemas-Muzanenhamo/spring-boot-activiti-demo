package com.activiti.demo.exception;

public class InvalidProcessInstanceKey extends RuntimeException {
    public InvalidProcessInstanceKey(String message) {
        super(message);
    }
}
