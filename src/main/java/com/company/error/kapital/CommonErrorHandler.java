package com.company.error.kapital;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CommonErrorHandler extends ResponseEntityExceptionHandler {
    private static final String GLOBAL_VALIDATION_ROOT = "";
    @Resource
    private MessageSource messageSource;

    public CommonErrorHandler() {
    }

//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    @ExceptionHandler({TokenExpiredException.class})
//    public RestErrorResponse handleTokenExpiredException(TokenExpiredException ex) {
//        log.error("Token expired, uuid: {}, code: {}, message: {}", new Object[]{ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage()});
//        return new RestErrorResponse(ex.getErrorUuid(), HttpStatus.NOT_ACCEPTABLE, ex.getErrorMessage());
//    }

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({NotAuthenticatedException.class})
//    public RestErrorResponse handleNotAuthenticatedException(NotAuthenticatedException ex) {
//        log.error("Token not valid, uuid: {}, code: {}, message: {}", new Object[]{ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage()});
//        return new RestErrorResponse(ex.getErrorUuid(), HttpStatus.UNAUTHORIZED, ex.getErrorMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public RestErrorResponse handleServiceExceptions(ServiceException ex) {
        return new RestErrorResponse(null,null,"Kanan is called",null,null);
//        log.error("Service error, uuid: {}, code: {}, message: {}, {}", new Object[]{ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage(), ex.formatProperties()});
//        return new RestErrorResponse(ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage(), ex.getProperties());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({CommonException.class})
//    public RestErrorResponse handleCommonException(CommonException ex) {
//        log.error("Error unexpected internal server error, {}, message: {}", ex, ex.getMessage());
//        return new RestErrorResponse(ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage());
//    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler({CommonDataNotFoundException.class})
//    public RestErrorResponse handleNotFoundException(CommonDataNotFoundException ex) {
//        log.error("Error data not found, uuid: {}, code: {}, message: {}", new Object[]{ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage()});
//        return new RestErrorResponse(ex.getErrorUuid(), ex.getErrorCode(), ex.getErrorMessage());
//    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public RestErrorResponse handleInternalServerErrors(Exception ex) {
        String uuid = this.generateUID();
        log.error("Error unexpected internal server error, {}, message: {}", ex, ex.getMessage());
        return new RestErrorResponse(uuid, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public RestErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        String uuid = this.generateUID();
        log.error("Constraint violation error, uuid: {}, message: {}", uuid, ex.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ValidationError> checks = (List)constraintViolations.stream().map((constraintViolation) -> {
            return new ValidationError("ErrorLevel.ERROR", constraintViolation.getPropertyPath().toString(), this.messageSource.getMessage(constraintViolation.getMessage(), (Object[])null, Locale.ENGLISH));
        }).collect(Collectors.toList());
        return new RestErrorResponse(uuid, HttpStatus.BAD_REQUEST, checks);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uuid = this.generateUID();
        System.out.println("CommonError = "+ex);
        log.error(ex.toString());
        log.error("Method argument not valid, uuid: {}, message: {}", uuid, ex.getMessage());
        List<ValidationError> checks = new ArrayList();
        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll((Collection)bindingResult.getFieldErrors().stream().map((fieldError) -> {
            return new ValidationError("ErrorLevel.ERROR", fieldError.getField(), this.messageSource.getMessage((String) Objects.requireNonNull(fieldError.getDefaultMessage()), fieldError.getArguments(), Locale.ENGLISH));
        }).collect(Collectors.toList()));
        checks.addAll((Collection)bindingResult.getGlobalErrors().stream().map((globalError) -> {
            return new ValidationError("ErrorLevel.ERROR", "", this.messageSource.getMessage((String)Objects.requireNonNull(globalError.getDefaultMessage()), globalError.getArguments(), Locale.ENGLISH));
        }).collect(Collectors.toList()));
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new RestErrorResponse(uuid, badRequest, checks), headers, badRequest);
    }

    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uuid = this.generateUID();
        log.error("Missing request param error, uuid: {}, message: {}", uuid, ex.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new RestErrorResponse(uuid, badRequest, ex.getMessage()), headers, badRequest);
    }

    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uuid = this.generateUID();
        log.error("Missing path variable error, uuid: {}, message: {}", uuid, ex.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new RestErrorResponse(uuid, badRequest, ex.getMessage()), headers, badRequest);
    }

    private String generateUID() {
        return UUID.randomUUID().toString();
    }
}