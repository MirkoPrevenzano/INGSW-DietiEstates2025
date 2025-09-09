package com.dietiEstates.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiestates.backend.dto.request.UpdatePasswordDto;
import com.dietiestates.backend.model.entity.Administrator;

import com.dietiestates.backend.repository.UserRepository;
import com.dietiestates.backend.service.UserService;


/*Strategia usate black-box: questa strategia effettua i casi di test è effettuata considerando i requisiti funzionali.
Si effettua analizzando i parametri e partizionare il dominio in sottoinsiemi. Tali sotto insiemi sono
detti classi di equivalenza.
L' approccio scelto per testare è quello di R-WECT. Qeest'approccio consiste che ogni classe di equivalenza
di un parametro deve essere presente in un test-case; nel caso di classi d'equivalenza di valori non validi, questi devono essere
testati in modo isolato, cioè preso un parametro con valore non valido nello stesso test case tutti gli altri parametri devono assumere valori validi.
*/

/*Funzione: updatePassword(String username, UpdatePasswordDto) 
 * String username:
 * CE1: null (non valido) OK
 * CE2: stringa vuota (non valido) OK
 * CE3: username non esistente (non valido) OK
 * CE4: username esistente (valido) OK
 * 
 * UpdatePasswordDto:
 * CE0: updatePasswordDto valido
 * CE1: vecchia password non corrispondente (non valido) OK
 * CE2: vecchia password uguale a quella nuova (non valido) OK
 * 
 * Questi casi successivi è compito del controller validarli
 * CE3: oldPassword vuota 
 * CE4: oldPassword null 
 * CE5: newPassword vuota
 * CE6: newPassword null 
 * CE7: UpdatePasswordDto null
 * CE8: newPassword con meno di 10 caratteri
 * CE9: newPassword senza  una lettera miniscola 
 * CE10: newPassword senza un numero
 * CE11: newPassword senza un carattere speciale
 * CE12: newPassword senza  una lettera maiuscola
 */

// ExtendWith serve per estendere le funzionalità di JUnit con quelle di Mockito




@ExtendWith(MockitoExtension.class)
@DisplayName("Test per verificare cambio password")
public class ChangePasswordTest {

    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordTest.class);
    private static final String VALID_USERNAME = "mirko.prevenzano2002@gmail.com";
    private static final String CURRENT_PASSWORD = "CurrentPassword123!";
    private static final String NEW_PASSWORD = "NewPassword123!";
    private static final String VALID_NAME = "mirko";
    private static final String VALID_SURNAME = "prevenzano";

    //Mock serve per creare oggetti fittizi che simulano il comportamento di oggetti reali 
    @Mock 
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
    //InjectMocks crea un'istanza della classe specificata e inietta i mock creati con @Mock

    Administrator user;
    UpdatePasswordDto updatePasswordDto;
    @BeforeEach
    void setUp(){
        logger.info(" SETUP - Inizializzazione test per cambio password");
        
        user = new Administrator();
        user.setUsername(VALID_USERNAME);
        user.setName(VALID_NAME);
        user.setSurname(VALID_SURNAME);
        user.setPassword(CURRENT_PASSWORD);
        
        updatePasswordDto = new UpdatePasswordDto(CURRENT_PASSWORD, NEW_PASSWORD);
        
        
    }

    
    @Test
    @DisplayName("TC1: username esistente + UpdatePasswordDto valida -> successo")
    void testValidUsernameValidPassowrd(){
        logger.info("TEST-1 STARTED - Testing valid username with valid password");
        
        
        
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()))
            .thenReturn(true);

        when(passwordEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword()))
            .thenReturn(false);

        when(passwordEncoder.encode(updatePasswordDto.getNewPassword()))
            .thenReturn("encodedNewPassword");

        
        assertDoesNotThrow(() -> userService.updatePassword(user.getUsername(), updatePasswordDto));
        assertEquals("encodedNewPassword", user.getPassword());
        
    }

    @Test
    @DisplayName("TC2: username non esistente")
    void testUsernameNotFound(){
        
        
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.updatePassword(user.getUsername(), updatePasswordDto));
        
    }

    @Test
    @DisplayName("TC3: username vuoto")
    void testEmptyUsername(){

        user.setUsername("");
                when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
            userService.updatePassword(user.getUsername(), updatePasswordDto)
        );
    }

    @Test
    @DisplayName("TC4: username null")
    void testNullUsername(){

        user.setUsername(null);
        
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
            userService.updatePassword(user.getUsername(), updatePasswordDto)
        );        
    }

    
    @Test
    @DisplayName("TC5: caso in cui oldPassword non corrisponde con l'attuale password")
    void testWrongPassword(){
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));
        
        when(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()))
            .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
            userService.updatePassword(user.getUsername(), updatePasswordDto));

        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(user.getUsername(), updatePasswordDto));
    }

    @Test
    @DisplayName("TC6: caso in cui newPassword corrisponde con l'attuale password")
    void testSamePassword(){

        
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));
        
        when(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()))
            .thenReturn(true);
        
        when(passwordEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword()))
            .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            userService.updatePassword(user.getUsername(), updatePasswordDto)
        );

    }

    
    
    @Test
    @DisplayName("TC7: UpdatePasswordDto null")
    void testNullUpdatePasswordDto(){

        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

        verifyNoInteractions(passwordEncoder);

        assertThrows(NullPointerException.class, () ->
            userService.updatePassword(user.getUsername(), null)
        );
        
        assertThrows(NullPointerException.class, () -> userService.updatePassword(user.getUsername(), null));
    }



}


