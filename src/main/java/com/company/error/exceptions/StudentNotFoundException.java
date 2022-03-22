package com.company.error.exceptions;

import com.company.error.ErrorCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class StudentNotFoundException extends ServiceException {

    public StudentNotFoundException(String message) {
        super(ErrorCode.STUDENT_NOT_FOUND.code(), message);
    }

}