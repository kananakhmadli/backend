package com.company.error.validation.constraints;

public interface ErrMessage {
    String FIRSTNAME_SIZE = "validation.firstName.size.message";
    String FIRSTNAME_NOT_NULL = "validation.firstName.notNull.message";

    String LASTNAME_SIZE = "validation.lastName.size.message";
    String LASTNAME_NOT_NULL = "validation.lastName.notNull.message";

    String EMAIL_SIZE = "validation.email.size.message";
    String EMAIL_NOT_NULL = "validation.email.notNull.message";
    String EMAIL_UNIQUE = "validation.email.uniqueEmail.message";

}