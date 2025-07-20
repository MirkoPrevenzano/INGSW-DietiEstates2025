
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class EmailValidatorImpl implements ConstraintValidator<EmailValidator,CharSequence> 
{

/*     private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]" 
                                                              + "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]*" 
                                                              + "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]{1}" 
                                                              + "@" 
                                                              + "[a-z0-9]{1}"  
                                                              + "[a-z0-9-]*"  
                                                              + "\\."  
                                                              + "[a-z0-9-]*" 
                                                              + "[a-z0-9]$"); */

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

        String emailStr = email.toString();
        int atIndex = emailStr.indexOf('@');
        if (atIndex != -1 && atIndex > 64) 
            return false; 
            
        return emailPattern.matcher(email).matches() && email.length() <= 320;
    }
}