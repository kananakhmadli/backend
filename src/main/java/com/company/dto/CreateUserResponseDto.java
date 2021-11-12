package com.company.dto;

import com.company.validator.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;


}
