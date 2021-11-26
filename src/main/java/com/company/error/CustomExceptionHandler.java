package com.company.error;

import az.kapitalbank.front.lib.error.model.ErrorLevel;
import az.kapitalbank.front.lib.error.model.RestErrorResponse;
import az.kapitalbank.front.lib.error.model.ValidationError;
import com.company.error.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(),
                        new Date(),
                        "Some things no found", ex.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> others(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(), "Exception happened", ex.getMessage()));
    }

    @Resource
    private MessageSource messageSource;

    private String errorMessage(ObjectError objectError) {
        try {
            return messageSource.getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
                    objectError.getArguments(), Locale.ENGLISH);
        } catch (Exception ex) {
            return objectError.getDefaultMessage();
        }
    }


//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
////        return ResponseEntity.status(HttpStatus.SEE_OTHER)
////                .body(new ExceptionResponse(HttpStatus.SEE_OTHER.value(),
////                        new Date(), "Validation Failed",
////                        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
//        return ResponseEntity.status(HttpStatus.SEE_OTHER)
//                .body(new ExceptionResponse(HttpStatus.SEE_OTHER.value(),
//                        new Date(), "Validation Failed",
//                        messageSource.getMessage(ex.getMessage(), ex.getStackTrace(), Locale.ENGLISH)));
//    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Method argument not valid, uuid: {}, message: {}", uuid, ex.getMessage());
        List<ValidationError> checks = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll((Collection) bindingResult.getFieldErrors().stream().map((fieldError) -> {
            return new ValidationError(ErrorLevel.ERROR, fieldError.getField(), this.messageSource.getMessage((String) Objects.requireNonNull(fieldError.getDefaultMessage()), fieldError.getArguments(), Locale.ENGLISH));
        }).collect(Collectors.toList()));
        checks.addAll((Collection) bindingResult.getGlobalErrors().stream().map((globalError) -> {
            return new ValidationError(ErrorLevel.ERROR, "", this.messageSource.getMessage((String) Objects.requireNonNull(globalError.getDefaultMessage()), globalError.getArguments(), Locale.ENGLISH));
        }).collect(Collectors.toList()));
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new RestErrorResponse(uuid, badRequest, checks), headers, badRequest);
    }


//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    @ResponseStatus(HttpStatus.SEE_OTHER)
//    public ResponseEntity<ExceptionResponse> validationException2(MethodArgumentNotValidException ex) {
//        return ResponseEntity.status(HttpStatus.SEE_OTHER)
//                .body(new ExceptionResponse(HttpStatus.SEE_OTHER.value(),
//                new Date(), "Validation Failed",
//                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
//    }


//    protected ResponseEntity<Object> handleBindException(BindException ex,
//                                                         HttpHeaders headers,
//                                                         HttpStatus status,
//                                                         WebRequest request) {
//        String uuid = UUID.randomUUID().toString();
//        List<ValidationError> checks = new ArrayList<>();
//
//        BindingResult bindingResult = ex.getBindingResult();
//        checks.addAll(bindingResult.getFieldErrors()
//                .stream()
//                .map((fieldError) -> new ValidationError("bad", fieldError.getField(),errorMessage(fieldError)))
//                .collect(Collectors.toList()));
//
//        checks.addAll(bindingResult.getGlobalErrors()
//                .stream()
//                .map((globalError) -> new ValidationError("BAD", "", errorMessage(globalError)))
//                .collect(Collectors.toList()));
//
//        System.out.println(checks.toString());
//        logger.error("Error happened");
////        return new ResponseEntity<>(new RestErrorResponse(uuid, HttpStatus.BAD_REQUEST, checks),
////                headers,
////                HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
//                new Date(),checks.toString(),ex.getMessage()),headers,HttpStatus.BAD_REQUEST);
//    }
}