
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator,CharSequence> 
{
    private static final Pattern passwordPattern1 = Pattern.compile("[A-Z]+");
    private static final Pattern passwordPattern2 = Pattern.compile("\\d+");
    private static final Pattern passwordPattern3 = Pattern.compile("[!-/,:-@,{-}]+");



    @Override
    public boolean isValid(CharSequence password, ConstraintValidatorContext context) 
    {
        if(!(password.length() >= 10 && passwordPattern1.matcher(password).find() 
            && passwordPattern2.matcher(password).find() && passwordPattern3.matcher(password).find()))
        {
            return false;
        }       

        return true;
    }
}