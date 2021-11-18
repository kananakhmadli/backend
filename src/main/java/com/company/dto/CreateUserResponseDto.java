package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
