
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



/* public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator,CharSequence> 
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

        return truee;
    }
}
 */


public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, CharSequence> 
{
        
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGITS = Pattern.compile("\\d");
    private static final Pattern SYMBOLS = Pattern.compile("[!-/,:-@,{-}]");
    

    @Override
    public boolean isValid(CharSequence password, ConstraintValidatorContext context) 
    {        
        System.out.println("\n\n\nYTOOOOOO\n\n\n");
        context.disableDefaultConstraintViolation();
        boolean hasExceptionOccurred = false;
        
        if (password.length() < 10) 
        {
            addViolation(context, "La password deve essere di almeno 10 caratteri");
            hasExceptionOccurred = true;
        }
        
        if (!UPPERCASE.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una lettera maiuscola");
            hasExceptionOccurred = true;
        }
        
        if (!LOWERCASE.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una lettera minuscola");
            hasExceptionOccurred = true;
        }
        
        if (!DIGITS.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una cifra");
            hasExceptionOccurred = true;
        }
        
        if (!SYMBOLS.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno un carattere speciale");
            hasExceptionOccurred = true;
        }
        
        return !hasExceptionOccurred;
    }


    private void addViolation(ConstraintValidatorContext context, String message) 
    {        
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}
