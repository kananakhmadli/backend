package com.company.error.validation;

import com.company.error.validation.constraints.UniqueEmail;
import com.company.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmilValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    public StudentRepository studentRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return studentRepository.checkByEmail(email);
    }

}