package com.company.dto.request;

import com.company.error.validation.constraints.ErrMessage;
import com.company.error.validation.constraints.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStudentRequest {

    @NotNull(message = ErrMessage.ID_NOT_NULL)
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

    @UniqueEmail
    private String email;
    private String password;

}