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
public class CreateUserRequestDto {
    private Long id;

    @NotNull(message = "{backend.constraint.firstName.notNull.message}")
    @Size(min = 4, max = 10, message = "{backend.constraint.firstName.size.message}")
    private String firstName;

    @NotNull(message = "{backend.constraint.lastName.notNull.message}")
    @Size(min = 3, max = 9, message = "{backend.constraint.lastName.size.message}")
    private String lastName;

    @JsonProperty("Yas") //olduqu kimi yazılmalıdır
    private Integer age;

    @NotNull(message = "{backend.constraint.email.notNull.message}")
    @Size(min = 3, max = 30, message = "{backend.constraint.email.size.message}")
    @UniqueEmail
    private String email;
    private String password;


}
