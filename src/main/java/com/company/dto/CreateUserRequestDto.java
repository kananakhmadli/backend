package com.company.dto;

import com.company.error.validation.constraints.ErrMessage;
import com.company.error.validation.constraints.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequestDto {
    private Long id;

    @NotNull(message = ErrMessage.FIRSTNAME_NOT_NULL)
    @Size(min = 4, max = 10, message = ErrMessage.FIRSTNAME_SIZE)
    @ApiModelProperty(notes = "Name of the contact.",
            example = "Jessica", required = false, position = 1)
    @NotBlank
    private String firstName;

    @NotNull(message = ErrMessage.LASTNAME_NOT_NULL)
    @Size(min = 3, max = 9, message = ErrMessage.LASTNAME_SIZE)
    private String lastName;

    @JsonProperty("age")
    private Integer age;

    @NotNull(message = ErrMessage.EMAIL_NOT_NULL)
    @Size(min = 3, max = 30, message = ErrMessage.EMAIL_SIZE)
    @UniqueEmail
    private String email;
    private String password;
}