package com.company.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RestResponse {

    private String uuid = UUID.randomUUID().toString();
    private String message;
    private Object object;

}