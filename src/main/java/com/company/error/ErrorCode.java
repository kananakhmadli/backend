package com.company.error;

public enum ErrorCode {

    STUDENT_NOT_FOUND,
    UNKNOWN_ERROR;

    public String code() {
        return this.name();
    }

}