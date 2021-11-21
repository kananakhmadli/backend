package com.company.error.validation;

import com.company.repository.UserRepository;
import com.company.error.validation.constraints.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmilValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    public UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.checkByEmail(email);
    }
}