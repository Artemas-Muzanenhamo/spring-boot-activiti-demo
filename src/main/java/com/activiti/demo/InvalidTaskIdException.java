package com.activiti.demo;

public class InvalidTaskIdException extends RuntimeException {
    public InvalidTaskIdException() {
    }

    public InvalidTaskIdException(String message) {
        super(message);
    }
}
