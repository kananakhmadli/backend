//package com.company.error;
//
//import com.company.error.exceptions.UserNotFoundException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.Date;
//
//@RestControllerAdvice
//public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = {UserNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(),
//                        new Date(),
//                        "Some things no found", ex.getMessage()));
//    }
//
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
//        return ResponseEntity.status(HttpStatus.SEE_OTHER)
//                .body(new ExceptionResponse(HttpStatus.SEE_OTHER.value(),
//                        new Date(), "Validation Failed",
//                        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
//    }
//
////    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
////    @ResponseStatus(HttpStatus.SEE_OTHER)
////    public ResponseEntity<ExceptionResponse> validationException2(MethodArgumentNotValidException ex) {
////        return ResponseEntity.status(HttpStatus.SEE_OTHER)
////                .body(new ExceptionResponse(HttpStatus.SEE_OTHER.value(),
////                new Date(), "Validation Failed",
////                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
////    }
//
//    @ExceptionHandler(value = {Exception.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ExceptionResponse> others(Exception ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
//                        new Date(), "Exception happened", ex.getMessage()));
//    }
//}