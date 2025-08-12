
package com.dietiEstates.backend.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.experimental.UtilityClass;


@UtilityClass
public class PasswordGenerationUtil
{
    private final int DEFAULT_PASSWORD_LENGTH = 12;

    private final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String DIGITS = "0123456789";
    private final String SPECIAL_CHARS = "!@#$%^&*()_+{}[]:;\"'<>,.?/\\|`~";
    private final String ALL_POSSIBLE_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS;

    private final SecureRandom SECURE_RANDOM = new SecureRandom();



    public String generateRandomPassword() 
    {
        List<Character> passwordListChars = new ArrayList<>(DEFAULT_PASSWORD_LENGTH);

        passwordListChars.add(LOWERCASE_CHARS.charAt(SECURE_RANDOM.nextInt(LOWERCASE_CHARS.length())));
        passwordListChars.add(UPPERCASE_CHARS.charAt(SECURE_RANDOM.nextInt(UPPERCASE_CHARS.length())));
        passwordListChars.add(DIGITS.charAt(SECURE_RANDOM.nextInt(DIGITS.length())));
        passwordListChars.add(SPECIAL_CHARS.charAt(SECURE_RANDOM.nextInt(SPECIAL_CHARS.length())));

        for (int i = passwordListChars.size(); i < DEFAULT_PASSWORD_LENGTH; i++) 
        {
            int randomIndex = SECURE_RANDOM.nextInt(ALL_POSSIBLE_CHARS.length());
            passwordListChars.add(ALL_POSSIBLE_CHARS.charAt(randomIndex));
        }

        Collections.shuffle(passwordListChars, SECURE_RANDOM);

        char[] passwordArrayChars = new char[passwordListChars.size()];
        for (int i = 0; i < passwordListChars.size(); i++) 
            passwordArrayChars[i] = passwordListChars.get(i);
            

        return new String(passwordArrayChars);
    }
}