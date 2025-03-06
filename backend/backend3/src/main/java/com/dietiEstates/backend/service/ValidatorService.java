
package com.dietiEstates.backend.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;


@Service
@Slf4j
public class ValidatorService 
{
    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]" 
                                                              + "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]*" 
                                                              + "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]{1}" 
                                                              + "@" 
                                                              + "[a-z0-9]{1}"  
                                                              + "[a-z0-9-]*"  
                                                              + "\\."  
                                                              + "[a-z0-9-]*" 
                                                              + "[a-z0-9]$");
    private static final Pattern passwordPattern1 = Pattern.compile("[A-Z]+");
    private static final Pattern passwordPattern2 = Pattern.compile("\\d+");
    private static final Pattern passwordPattern3 = Pattern.compile("[!-/,:-@,{-}]+");


    void emailValidator(String email) throws IllegalArgumentException
    {
        if(!(emailPattern.matcher(email).matches() && email.length() <= 320))
        {
            throw new IllegalArgumentException("You have entered an invalid e-mail!");
        }
    }


    void passwordValidator(String password) throws IllegalArgumentException
    {
        if(!(password.length() >= 10 && passwordPattern1.matcher(password).find() 
            && passwordPattern2.matcher(password).find() && passwordPattern3.matcher(password).find()))
        {
            throw new IllegalArgumentException("You have entered an invalid password!");
        }         
    }
    

    //TODO methods:
    //usernamevalidator  
}