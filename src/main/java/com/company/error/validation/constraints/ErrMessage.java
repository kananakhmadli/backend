package com.company.error.validation.constraints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrMessage {

    public static final String FIRSTNAME_SIZE = "err.firstNameSize.message";
    public static final String FIRSTNAME_NOT_NULL = "err.firstNameNotNull.message";
    public static final String LASTNAME_SIZE = "err.lastNameSize.message";
    public static final String LASTNAME_NOT_NULL = "err.lastNameNotNull.message";
    public static final String EMAIL_SIZE = "err.emailSize.message";
    public static final String EMAIL_NOT_NULL = "err.emailNotNull.message";
    public static final String EMAIL_UNIQUE = "err.emailUniqueEmail.message";
    public static final String PASSWORD_NOT_NULL = "err.passwordNotNull.message";
    public static final String ID_NOT_NULL = "err.idNotNull.message";

}