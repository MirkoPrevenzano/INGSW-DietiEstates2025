package com.dietiEstates.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dietiestates.backend.validator.PasswordValidatorImpl;

import jakarta.validation.ConstraintValidatorContext;


/**
 *     isValid(String password, ConstraintValidatorContext context)
 * 
 *  String password:
 * CE1: null (non valido)
 * CE2: vuoto (non valido)
 * CE3: solo spazi (non valido)
 * CE4: valido (valido)
 * CE5: meno di 10 caratteri (non valido)
 * CE6: senza una lettera maiuscola (non valido)
 * CE7: senza una lettera minuscola (non valido)
 * CE8: senza un numero (non valido)
 * CE9: senza un carattere speciale (non valido)
 * 
 * 
 * ConstraintValidatorContext context:
 * CE1: null (non valido)
 * CE2: non null (valido)
 * 
 *
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Test per verificare la validazione password")
class PasswordValidatorTest {
  
    private PasswordValidatorImpl passwordValidator;
    private ConstraintValidatorContext mockContext;

    private static final String VALID_PASSWORD = "validPassword123!";
    private static final String INVALID_LENGTH_PASSWORD = "Short1!";
    private static final String NO_UPPERCASE_PASSWORD = "nouppercase1!";
    private static final String NO_LOWERCASE_PASSWORD = "NOLOWERCASE1!";
    private static final String NO_NUMBER_PASSWORD = "NoNumber!";
    private static final String NO_SPECIAL_CHAR_PASSWORD = "NoSpecialChar1";
    private static final String EMPTY_USERNAME = "";
    private static final String NULL_USERNAME = null;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidatorImpl();
        
        mockContext = mock(ConstraintValidatorContext.class);
    }

    private void setupMockContextForInvalidPassword() {
        ConstraintValidatorContext.ConstraintViolationBuilder mockBuilder = 
            mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(mockContext.buildConstraintViolationWithTemplate(anyString()))
            .thenReturn(mockBuilder);
    }

    @Test
    @DisplayName("TC1: Password valida")
    void validPasswordTest() {
        assertTrue(passwordValidator.isValid(VALID_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC2: Password null")
    void nullPasswordTest() {
        assertThrows(NullPointerException.class, () -> 
            passwordValidator.isValid(NULL_USERNAME, mockContext));
    }

    @Test
    @DisplayName("TC3: Password vuota")
    void emptyPasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(EMPTY_USERNAME, mockContext));
    }

    @Test
    @DisplayName("TC4: Password con meno di 10 caratteri")
    void shortPasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(INVALID_LENGTH_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC5: Password senza una lettera maiuscola")
    void noUppercasePasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(NO_UPPERCASE_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC6: Password senza una lettera minuscola")
    void noLowercasePasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(NO_LOWERCASE_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC7: Password senza un numero")
    void noNumberPasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(NO_NUMBER_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC8: Password senza un carattere speciale")
    void noSpecialCharPasswordTest() {
        setupMockContextForInvalidPassword();
        assertFalse(passwordValidator.isValid(NO_SPECIAL_CHAR_PASSWORD, mockContext));
    }

    @Test
    @DisplayName("TC9: Context null")
    void nullContextTest() {
        assertThrows(NullPointerException.class, () -> {
            passwordValidator.isValid(VALID_PASSWORD, null);
        });
    }

   

  }
