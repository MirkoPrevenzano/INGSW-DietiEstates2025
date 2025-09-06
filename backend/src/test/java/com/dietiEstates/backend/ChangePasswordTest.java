package com.dietiEstates.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dietiestates.backend.dto.request.UpdatePasswordDto;
import com.dietiestates.backend.model.entity.User;
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
 * CE1: null (non valido)
 * CE2: stringa vuota (non valido)
 * CE3: username non esistente (non valido)
 * CE4: username esistente (valido) OK
 * 
 * UpdatePasswordDto:
 * CE1: vecchia password non corrispondente (non valido)
 * CE2: vecchia password uguale a quella nuova (non valido)
 * CE3: oldPassword vuota
 * CE4: oldPassword null
 * CE5: newPassword vuota
 * CE6: newPassword null
 * CE7: null
*/

// ExtendWith serve per estendere le funzionalità di JUnit con quelle di Mockito

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per verificare cambio password")
public class ChangePasswordTest {

    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordTest.class);
    private static final String VALID_USERNAME = "mirko.prevenzano2002@gmail.com";

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
        user.setName("mirko");
        user.setSurname("prev");
        user.setPassword("CurrentPassword123!");
        
        updatePasswordDto = new UpdatePasswordDto("CurrentPassword123!", "NewPassword123!");
        
        
    }

    @Test
    @DisplayName("TC1: username esistente + UpdatePasswordDto valida -> successo")
    void testValidUsernameValidPassowrd(){
        logger.info("TEST-1 STARTED - Testing valid username with valid password");
        
        
        // Mock setup
        logger.debug(" TEST-1 - Configurazione mock userRepository.findByUsername");
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

        logger.debug(" TEST-1 - Configurazione mock passwordEncoder.matches (old password)");
        when(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()))
            .thenReturn(true);

        logger.debug(" TEST-1 - Configurazione mock passwordEncoder.matches (new password)");
        when(passwordEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword()))
            .thenReturn(false);

        logger.debug(" TEST-1 - Configurazione mock passwordEncoder.encode");
        when(passwordEncoder.encode(updatePasswordDto.getNewPassword()))
            .thenReturn("encodedNewPassword");

        // Execution
        logger.info(" TEST-1 - Esecuzione metodo updatePassword");
        assertDoesNotThrow(() -> userService.updatePassword(user.getUsername(), updatePasswordDto));

        logger.debug(" TEST-1 - Verifica password aggiornata");
        assertEquals("encodedNewPassword", user.getPassword());
        
        logger.info("✅ TEST-1 PASSED - Test completato con successo");
    }

    @Test
    @DisplayName("TC2: username non esistente")
    void testUsernameNotFound(){
        logger.info(" TEST-2 STARTED - Testing username not found scenario");
        
        
        logger.debug(" TEST-2 - Configurazione mock userRepository.findByUsername per restituire Optional.empty()");
        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.empty());

        logger.info(" TEST-2 - Esecuzione metodo updatePassword (dovrebbe lanciare UsernameNotFoundException)");
        assertThrows(UsernameNotFoundException.class, () ->
            userService.updatePassword(user.getUsername(), updatePasswordDto)
        );
        
        logger.info("✅ TEST-2 PASSED - UsernameNotFoundException correttamente lanciata");
    }

   
}
