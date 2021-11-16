package com.company.error;

import com.company.error.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse userNotFound(UserNotFoundException ex) {

        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), new Date(), "Some things no found", ex.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public ExceptionResponse foo(MethodArgumentNotValidException ex) {
        return new ExceptionResponse(HttpStatus.SEE_OTHER.value(), new Date(), "Validation Failed", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse others(Exception ex) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), new Date(),"Exception happened", ex.getMessage());
    }


}