package com.company.error.validation.constraints;

public interface ErrMessage {
    String FIRSTNAME_SIZE = "backend.constraint.firstName.size.message";
    String FIRSTNAME_NOT_NULL = "backend.constraint.firstName.notNull.message";

    String LASTNAME_SIZE = "backend.constraint.lastName.size.message";
    String LASTNAME_NOT_NULL = "backend.constraint.lastName.notNull.message";

    String EMAIL_SIZE = "backend.constraint.email.size.message";
    String EMAIL_NOT_NULL = "backend.constraint.email.notNull.message";
    String EMAIL_UNIQUE = "backend.constraint.email.uniqueEmail.message";

}
