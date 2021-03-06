package com.activiti.demo;

import com.activiti.demo.exception.*;
import org.activiti.engine.ActivitiObjectNotFoundException;
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
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTaskIdException.class)
    public ResponseEntity<String> invalidParametersException(InvalidTaskIdException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProcessNameException.class)
    public ResponseEntity<String> invalidProcessNameException(InvalidProcessNameException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProcessInstanceKeyException.class)
    public ResponseEntity<String> invalidProcessInstanceKey(InvalidProcessInstanceKeyException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTaskAssigneeException.class)
    public ResponseEntity<String> invalidTaskAssigneeException(InvalidTaskAssigneeException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDeploymentIdException.class)
    public ResponseEntity<String> invalidDeploymentIdException(InvalidDeploymentIdException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ActivitiObjectNotFoundException.class)
    public ResponseEntity<String> activitiObjectNotFoundException(ActivitiObjectNotFoundException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
