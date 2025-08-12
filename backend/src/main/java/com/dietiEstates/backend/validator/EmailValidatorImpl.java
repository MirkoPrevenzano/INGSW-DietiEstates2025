
package com.dietiEstates.backend.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidatorImpl implements ConstraintValidator<EmailValidator,CharSequence> 
{
    private static final Pattern emailPattern = Pattern.compile(
                                                            "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]"                          
                                                            + "([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]|\\.(?!\\.))*"  
                                                            + "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]"                         
                                                            + "@"                                                       
                                                            + "[a-zA-Z0-9]"                                            
                                                            + "[a-zA-Z0-9-]*"                                          
                                                            + "[a-zA-Z0-9]"                                            
                                                            + "(\\.[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9])+"            
                                                            + "$", 
                                                            Pattern.CASE_INSENSITIVE
                                                        );

    @Override
    public boolean isValid(CharSequence email, ConstraintValidatorContext context) 
    {
        if (email.length() > 320) 
            return false;

        String emailString = email.toString();
        int atIndex = emailString.indexOf('@');
        if (atIndex != -1 && atIndex > 64) 
            return false; 
            
        return emailPattern.matcher(email).matches();
    }
}