package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto2 {

    private String name;
    private String surname;
    private String fullName;

    public String concatNames(String firstName, String lastName) {
        return firstName + lastName;
    }

}