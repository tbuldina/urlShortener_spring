package com.urlShortener.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by tbuldina on 06/04/2018.
 */
@Documented
@Constraint(validatedBy = UrlFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlFormatConstraint {
    String message() default "{url.invalid.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
