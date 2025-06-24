
package com.dietiEstates.backend.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.extra.ValidableEnum;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.User;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;



//@Component
@UtilityClass
@Slf4j
public class ValidationUtil
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



    public void emailValidator(String email) throws IllegalArgumentException
    {
        if(!(emailPattern.matcher(email).matches() && email.length() <= 320))
        {
            throw new IllegalArgumentException("You have entered an invalid e-mail!");
        }
    }


    public void passwordValidator(String password) throws IllegalArgumentException
    {
        if(!(password.length() >= 10 && passwordPattern1.matcher(password).find() 
            && passwordPattern2.matcher(password).find() && passwordPattern3.matcher(password).find()))
        {
            throw new IllegalArgumentException("You have entered an invalid password!");
        }         
    }
    
    
    public <E extends Enum<E>> E enumValidator(Class<E> enumClass, String value) 
    {
        for(E e : enumClass.getEnumConstants())
        {
            //e.name();
            System.out.printf("e.name = %s\nvalue: %s\n\n", ((ValidableEnum) e).getValue(), value);
            if ( ((ValidableEnum) e).getValue().equalsIgnoreCase(value) )
            {
                return e;
            }
        }

        throw new IllegalArgumentException("VALORE ENUM SBAGLIATO! Classe: " + enumClass.getSimpleName());
    }


    public <U extends User> U optionalUserValidator(Optional<U> optionalUser, String username) 
    {
        if(optionalUser.isEmpty())
        {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database!");
        }    

        log.info("User found in database: {}", username);
        return optionalUser.get();   
    }

    public <R extends RealEstate> R optionalRealEstateValidator(Optional<R> optionalRealEstate, Long id) 
    {
        if(optionalRealEstate.isEmpty())
        {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database!");
        }    

        log.info("User found in database: {}", id);
        return optionalRealEstate.get();   
    }

    
    //TODO methods:
    //usernamevalidator  
}