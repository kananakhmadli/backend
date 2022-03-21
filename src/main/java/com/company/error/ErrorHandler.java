package com.company.error;

import com.company.error.exceptions.ErrorLevel;
import com.company.error.exceptions.ErrorResponse;
import com.company.error.exceptions.UserNotFoundException;
import com.company.error.exceptions.ValidationError;
import io.micrometer.core.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("ConstantConditions")
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ErrorResponse userNotFound(UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {Exception.class})
    public ErrorResponse handleInternalServerErrors(Exception ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // bütün validationları göstermek üçün stream edirik
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        var checks = new ArrayList<ValidationError>();
        var bindingResult = ex.getBindingResult();

        checks.addAll(bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ValidationError(ErrorLevel.ERROR,
                        fieldError.getField(),
                        errorMessage(fieldError)))
                .collect(Collectors.toList()));

        checks.addAll(bindingResult.getGlobalErrors().stream()
                .map(globalError -> new ValidationError(ErrorLevel.ERROR,
                        globalError.getObjectName(),
                        errorMessage(globalError)))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Arguments", checks),
                headers,
                HttpStatus.BAD_REQUEST);
    }

    private String errorMessage(ObjectError objectError) {
        return messageSource.getMessage(
                Objects.requireNonNull(objectError.getDefaultMessage()),
                objectError.getArguments(),
                LocaleContextHolder.getLocale());
    }

}