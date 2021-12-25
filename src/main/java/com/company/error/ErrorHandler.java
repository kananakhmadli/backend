package com.company.error;

import com.company.error.exceptions.ErrorLevel;
import com.company.error.exceptions.ErrorResponse;
import com.company.error.exceptions.UserNotFoundException;
import com.company.error.exceptions.ValidationError;
import io.micrometer.core.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFound(UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInternalServerErrors(Exception ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // bütün validationları göstermek üçün stream edirik
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        List<ValidationError> errorList = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();

        errorList.addAll(bindingResult.getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ValidationError(ErrorLevel.ERROR, fieldError.getField(), this.messageSource.getMessage(fieldError.getDefaultMessage(), fieldError.getArguments(), Locale.ENGLISH))
                )
                .collect(Collectors.toList()));

        errorList.addAll(bindingResult.getGlobalErrors()
                .stream()
                .map(globalError ->
                        new ValidationError(ErrorLevel.ERROR, "", this.messageSource.getMessage(globalError.getDefaultMessage(), globalError.getArguments(), Locale.ENGLISH)))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, null, errorList), headers, HttpStatus.BAD_REQUEST);
    }

}