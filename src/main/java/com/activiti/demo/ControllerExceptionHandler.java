package com.activiti.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> invalidParametersException(NumberFormatException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>("Deployment Id is not valid", BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTaskIdException.class)
    public ResponseEntity<String> invalidParametersException(InvalidTaskIdException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
