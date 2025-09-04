
package com.dietiestates.backend.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, CharSequence> 
{
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");

    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");

    private static final Pattern DIGITS = Pattern.compile("\\d");

    private static final Pattern SYMBOLS = Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@{}\\[\\]|^_`~]");


    
    @Override
    public boolean isValid(CharSequence password, ConstraintValidatorContext context) 
    {        
        context.disableDefaultConstraintViolation();

        boolean hasExceptionOccurred = false;

        hasExceptionOccurred |= validateLength(password, context);

        hasExceptionOccurred |= validateUppercase(password, context);

        hasExceptionOccurred |= validateLowercase(password, context);

        hasExceptionOccurred |= validateDigits(password, context);

        hasExceptionOccurred |= validateSymbols(password, context);
        
        return !hasExceptionOccurred;
    }



    private boolean validateLength(CharSequence password, ConstraintValidatorContext context) 
    {
        if (password.length() < 10) 
        {
            addViolation(context, "La password deve essere di almeno 10 caratteri");
            
            return true;
        }

        return false;
    }


    private boolean validateUppercase(CharSequence password, ConstraintValidatorContext context) 
    {
        if (!UPPERCASE.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una lettera maiuscola");

            return true;
        }

        return false;
    }


    private boolean validateLowercase(CharSequence password, ConstraintValidatorContext context) 
    {
        if (!LOWERCASE.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una lettera minuscola");

            return true;
        }

        return false;
    }


    private boolean validateDigits(CharSequence password, ConstraintValidatorContext context) 
    {
        if (!DIGITS.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno una cifra");
            
            return true;
        }

        return false;
    }


    private boolean validateSymbols(CharSequence password, ConstraintValidatorContext context) 
    {
        if (!SYMBOLS.matcher(password).find()) 
        {
            addViolation(context, "La password deve contenere almeno un carattere speciale");
            
            return true;
        }

        return false;
    }


    private void addViolation(ConstraintValidatorContext context, String message) 
    {        
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}
