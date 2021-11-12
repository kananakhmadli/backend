package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private Integer errorCode;
    private String errorMessage;
    private String successMessage;
    private Object object;

    private ResponseDto() {

    }

    public static ResponseDto of(Object object) {
        ResponseDto dto = new ResponseDto();
        dto.object = object;
        return dto;
    }

    public static ResponseDto of(Object object, String successMessage) {
        ResponseDto dto = new ResponseDto();
        dto.object = object;
        dto.successMessage = successMessage;
        return dto;
    }

    public static ResponseDto of(Object object, String errorMessage, Integer errorCode) {
        ResponseDto dto = new ResponseDto();
        dto.object = object;
        dto.errorCode = errorCode;
        dto.errorMessage = errorMessage;
        return dto;
    }


}
