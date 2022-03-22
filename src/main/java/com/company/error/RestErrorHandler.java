package com.company.error;

import com.company.error.exceptions.StudentNotFoundException;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {StudentNotFoundException.class})
    public RestErrorResponse studentNotFound(StudentNotFoundException ex) {
        log.error("Service error, uuid: {}, code: {}, message: {}, {}",
                ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage(), ex.formatProperties());
        return new RestErrorResponse(ex.getErrorUuid(),
                ex.getErrorCode(),
                ex.getErrorMessage(),
                ex.getProperties());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public RestErrorResponse handleInternalServerErrors(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("Error unexpected internal server error, uuid: {}, message: {}",
                uuid, ex.getMessage());
        return new RestErrorResponse(uuid,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Internal server error");
    }

    // bütün validationları göstermek üçün stream edirik
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Constraint violation error, uuid: {}, message: {}", uuid, ex.getMessage());
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

        var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new RestErrorResponse(uuid, badRequest.name(), "Invalid Arguments", checks),
                headers,
                badRequest);
    }

    private String errorMessage(ObjectError objectError) {
        return messageSource.getMessage(
                Objects.requireNonNull(objectError.getDefaultMessage()),
                objectError.getArguments(),
                LocaleContextHolder.getLocale());
    }

}