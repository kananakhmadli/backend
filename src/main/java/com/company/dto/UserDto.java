package com.company.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

}