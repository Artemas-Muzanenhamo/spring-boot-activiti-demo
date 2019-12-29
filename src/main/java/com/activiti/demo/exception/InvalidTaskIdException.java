package com.activiti.demo.exception;

public class InvalidTaskIdException extends RuntimeException {
    public InvalidTaskIdException() {
    }

    public InvalidTaskIdException(String message) {
        super(message);
    }
}
