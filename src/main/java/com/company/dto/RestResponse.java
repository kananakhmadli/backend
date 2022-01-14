package com.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
public class RestResponse {

    private String uuid = UUID.randomUUID().toString();
    private String message;
    private Object object;

}