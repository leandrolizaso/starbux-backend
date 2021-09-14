package com.starbux.exception.handler;

import com.starbux.exception.NoToppingException;
import com.starbux.exception.ParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ResponseEntityExceptioHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NoToppingException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity handleNoToppingException(
            NoToppingException ex, WebRequest request) {
        logger.error(ex);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity handleParameterException(
            ParameterException ex, WebRequest request) {
        logger.error(ex);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}