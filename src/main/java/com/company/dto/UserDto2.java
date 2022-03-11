package com.company.dto;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class UserDto2 {

    private String name;
    private String surname;
    private String fullName;

    public String concatNames(String firstName, String lastName) {
        return firstName + lastName;
    }

}