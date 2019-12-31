package com.activiti.demo.exception;

public class InvalidDeploymentIdException extends RuntimeException {
    public InvalidDeploymentIdException(String message) {
        super(message);
    }
}
