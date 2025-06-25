
package com.dietiEstates.backend.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.experimental.UtilityClass;


@UtilityClass
public class PasswordGenerator 
{
    private static final int DEFAULT_PASSWORD_LENGTH = 12;

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+{}[]:;\"'<>,.?/\\|`~";
    private static final String ALL_POSSIBLE_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS;

    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();



    public static String generateRandomPassword() 
    {
        List<Character> passwordListChars = new ArrayList<>(DEFAULT_PASSWORD_LENGTH);

        passwordListChars.add(LOWERCASE_CHARS.charAt(RANDOM_GENERATOR.nextInt(LOWERCASE_CHARS.length())));
        passwordListChars.add(UPPERCASE_CHARS.charAt(RANDOM_GENERATOR.nextInt(UPPERCASE_CHARS.length())));
        passwordListChars.add(DIGITS.charAt(RANDOM_GENERATOR.nextInt(DIGITS.length())));
        passwordListChars.add(SPECIAL_CHARS.charAt(RANDOM_GENERATOR.nextInt(SPECIAL_CHARS.length())));

        for (int i = passwordListChars.size(); i < DEFAULT_PASSWORD_LENGTH; i++) 
        {
            int randomIndex = RANDOM_GENERATOR.nextInt(ALL_POSSIBLE_CHARS.length());
            passwordListChars.add(ALL_POSSIBLE_CHARS.charAt(randomIndex));
        }

        Collections.shuffle(passwordListChars, RANDOM_GENERATOR);

        char[] passwordArrayChars = new char[passwordListChars.size()];
        for (int i = 0; i < passwordListChars.size(); i++) 
            passwordArrayChars[i] = passwordListChars.get(i);
            

        return new String(passwordArrayChars);
    }
}
