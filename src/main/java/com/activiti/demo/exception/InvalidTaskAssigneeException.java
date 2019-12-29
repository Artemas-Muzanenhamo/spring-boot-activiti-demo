package com.activiti.demo.exception;

public class InvalidTaskAssigneeException extends RuntimeException {
    public InvalidTaskAssigneeException(String message) {
        super(message);
    }
}
