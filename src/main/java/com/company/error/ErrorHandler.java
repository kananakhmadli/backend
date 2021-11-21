package com.company.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends CommonErrorHandler {

    @Resource
    private MessageSource messageSource;

    @Override
//    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        String uuid = generateUID();
        System.out.println(ex+" Hey manan");
        log.error(ex.toString());
        log.error("Method argument not valid, uuid: {}, message: {}", uuid, ex.getMessage());
        List<ValidationError> checks = new ArrayList<>();

        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll(bindingResult.getFieldErrors().stream()
                .map((fieldError) -> new ValidationError("bad", fieldError.getField(),
                        errorMessage(fieldError)))
                .collect(Collectors.toList()));

        checks.addAll(bindingResult.getGlobalErrors().stream()
                .map((globalError) -> new ValidationError("BAD", "",
                        errorMessage(globalError)))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(new RestErrorResponse(uuid, HttpStatus.BAD_REQUEST, checks),
                headers,
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public RestErrorResponse handleInternalServerErrors(Exception ex) {
        String uuid = this.generateUID();
        BindingResult bindingResult=new BindException(ex,"hey");
        System.out.println(bindingResult);
        log.error("Error unexpected internal server error, {}, message: {}", ex, ex.getMessage());
        return new RestErrorResponse(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error");
    }

    private String generateUID() {
        return UUID.randomUUID().toString();
    }

    private String errorMessage(ObjectError objectError) {
        try {
            return messageSource.getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
                    objectError.getArguments(), Locale.ENGLISH);
        } catch (Exception ex) {
            return objectError.getDefaultMessage();
        }
    }
}