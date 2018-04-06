package com.urlShortener.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by tbuldina on 06/04/2018.
 */

public class UrlFormatValidator implements ConstraintValidator<UrlFormatConstraint, String> {

    public void initialize(UrlFormatConstraint longUrl) {
    }

    public boolean isValid(String longUrl, ConstraintValidatorContext context) {
        if (longUrl!=null) longUrl = longUrl.toLowerCase().trim();

        return !(longUrl.equals("http://")
                || longUrl.equals("https://")
                || longUrl.isEmpty());
    }

}
