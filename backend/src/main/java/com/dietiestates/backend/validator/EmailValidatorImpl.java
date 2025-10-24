
package com.dietiestates.backend.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, CharSequence> 
{
    private static final String LOCAL = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+";

    private static final String LOCAL_WITH_POINT = "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*+";
    
    private static final String DOMAIN = "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private static final String DOMAIN_WITH_POINT = "(?:\\.[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)++";

    private static final String LOCAL_PART = LOCAL + LOCAL_WITH_POINT;
    
    private static final String DOMAIN_PART = DOMAIN + DOMAIN_WITH_POINT;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^" + LOCAL_PART + "@" + DOMAIN_PART + "$", Pattern.CASE_INSENSITIVE);



    @Override
    public boolean isValid(CharSequence email, ConstraintValidatorContext context) 
    {
        if (email == null) 
            return true;
        
        if (email.length() > 320 || email.length() < 5) 
            return false;

        String emailString = email.toString();

        int atIndex = emailString.indexOf('@');
        if (atIndex != -1 && atIndex > 64) 
            return false; 
            
        return EMAIL_PATTERN.matcher(email)
                            .matches();
    }
}