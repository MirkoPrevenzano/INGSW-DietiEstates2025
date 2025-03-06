package com.example.datatier.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorService{


    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 10 &&
               password.chars().anyMatch(Character::isUpperCase) &&
               password.chars().anyMatch(Character::isDigit) &&
               password.chars().anyMatch(ch -> "!@#$%^&*()_+[]{}|;:,.<>?".indexOf(ch) >= 0);
    }
}