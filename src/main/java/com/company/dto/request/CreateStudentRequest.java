package com.company.dto.request;

import com.company.error.validation.constraints.ErrMessage;
import com.company.error.validation.constraints.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateStudentRequest {

    @Size(min = 4, max = 10, message = ErrMessage.FIRSTNAME_SIZE)
    @NotEmpty(message = ErrMessage.FIRSTNAME_NOT_NULL)
    private String firstName;

    @Size(min = 3, max = 9, message = ErrMessage.LASTNAME_SIZE)
    @NotEmpty(message = ErrMessage.LASTNAME_NOT_NULL)
    private String lastName;

    @JsonProperty("age")
    private Integer age;

    @UniqueEmail
    @Size(min = 3, max = 30, message = ErrMessage.EMAIL_SIZE)
    @NotEmpty(message = ErrMessage.EMAIL_NOT_NULL)
    private String email;

    @NotNull(message = ErrMessage.PASSWORD_NOT_NULL)
    private String password;

}
/*
@NotBlank(message = ErrMessage.FIRSTNAME_NOT_NULL)
*/