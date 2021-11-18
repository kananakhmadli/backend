package com.company.error.validation.constraints;

import com.company.error.validation.UniqueEmilValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueEmilValidator.class })
public @interface UniqueEmail {

    String message() default ErrMessage.EMAIL_UNIQUE;
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
