package com.company.error;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private int status;
    private Date timestamp;
    private String message;
    private String details;

}