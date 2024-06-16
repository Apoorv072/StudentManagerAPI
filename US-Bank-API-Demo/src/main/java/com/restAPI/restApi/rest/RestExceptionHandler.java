package com.restAPI.restApi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    ResponseEntity<RestExceptionErrorResponse> handleException(StudentNotFoundException e)
    {
        RestExceptionErrorResponse error = new RestExceptionErrorResponse();
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<RestExceptionErrorResponse> handleException(Exception e)
    {
        RestExceptionErrorResponse error = new RestExceptionErrorResponse();
        error.setMessage("Bad Request: "+e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
