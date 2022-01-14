package com.company.dto;

import com.company.error.validation.constraints.ErrMessage;
import com.company.error.validation.constraints.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = ErrMessage.ID_NOT_NULL)
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

    @UniqueEmail
    private String email;
    private String password;

}