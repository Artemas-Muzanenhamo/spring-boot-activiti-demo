package com.activiti.demo.web;

import com.activiti.demo.InvalidTaskIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
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
