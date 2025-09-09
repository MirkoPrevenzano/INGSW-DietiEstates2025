package com.dietiEstates.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dietiestates.backend.BackendApplication;
import com.dietiestates.backend.dto.request.CollaboratorCreationDto;
import com.dietiestates.backend.service.AdministratorService;
import com.fasterxml.jackson.databind.ObjectMapper;


/*
 * createCollaborator(CollaboratorCreationDto, Authentication)
 * CollaboratorCreationDto
 * CE1: tutti i campi validi 
 * CE2: username vuoto o null
 * CE3: name vuoto o null
 * CE4: lastName vuoto o null
 * CE5: tutti i campi vuoti o null
 * CE6: username già esistente
 * 
 * Authentication
 * CE1: valido 
 * CE2: null (non autenticata)
 * CE3: principal null
 * CE4: principal non è userDetails
 * CE5: Utente non abilitato a tale funzione
 * 
 * Spring security effettua dei controlli su Authentication in modo da evitare proprio l'accesso alla funzione del controller
 * nel caso in cui l'utente non è autenticato o non autorizzato.
 * 
 * Quindi le classi di equivalenza che vale la pena testare sono le seguenti:
 * CE1: valido
 * CE2: errore generico
 */

@DisplayName("Test per verificare creazione agenzia immobiliare")
@SpringBootTest(classes = BackendApplication.class) //Avvia il contesto completo di spring specificando la sua classe princiaple
@AutoConfigureMockMvc //Permette di configurare automaticamente mockMvc e permette di testare i controller senza avviare il server
/*@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration," +
    "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration," +
    "org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration"
})*/ //disabilità i filtri di sicurezza
class CreateCollaboratorTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean //permette di iniettare un bean in automatico simulando il comportamento reale
    private AdministratorService administratorService;

    @Autowired
    private ObjectMapper objectMapper; //permette di serializzare e deserializzare JSON


    CollaboratorCreationDto collaboratorCreationDto;
    Authentication authentication;
    @BeforeEach
    void setUp(){

        collaboratorCreationDto = new CollaboratorCreationDto();
        collaboratorCreationDto.setName("nome");
        collaboratorCreationDto.setSurname("surname");
        collaboratorCreationDto.setUsername("nome@mail.com");

        authentication = new UsernamePasswordAuthenticationToken("admin@mail.com", "pwd");
    }



    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"Admin"}) //Annotazione di spring security test che simula un utente autenticato
    @DisplayName("TC1: caso in cui i parametri sono validi. Utente da creare non esista, l'amministratore è autenticato correttamente e i parametri name e lastName siano non vuoti")
    void validTestCreateCollaborator() throws Exception {
        callControllerIsCreated();
        verify(administratorService).createCollaborator(eq("admin@mail.com"), any(CollaboratorCreationDto.class));    
    }

    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"Admin"})
    @DisplayName("TC2: caso in cui username del collaboratore da inserire è null o vuoto")
    void emptyUsernameCreateCollaborator() throws Exception {
        collaboratorCreationDto.setUsername(null);

        callControllerBadRequest();
        verifyNoInteractions(administratorService);

        collaboratorCreationDto.setUsername("");

        callControllerBadRequest();
        verifyNoInteractions(administratorService);
    }


    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"Admin"})
    @DisplayName("TC3: caso in cui name del collaboratore da inserire è null o vuoto")
    void emptyNameCreateCollaborator() throws Exception {
        collaboratorCreationDto.setName(null);

        callControllerBadRequest();
        verifyNoInteractions(administratorService);

        collaboratorCreationDto.setName("");

        callControllerBadRequest();
        verifyNoInteractions(administratorService);
    }

    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"Admin"})
    @DisplayName("TC4: caso in cui surname del collaboratore da inserire è null o vuoto")
    void emptySurnameCreateCollaborator() throws Exception {
        collaboratorCreationDto.setSurname(null);

       

        callControllerBadRequest();
        verifyNoInteractions(administratorService);

        collaboratorCreationDto.setSurname("");

        callControllerBadRequest();
        verifyNoInteractions(administratorService);
    }

    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"Admin"})
    @DisplayName("TC4: caso in cui surname del collaboratore da inserire è null o vuoto")
    void emptyFieldsCreateCollaborator() throws Exception {
        collaboratorCreationDto.setSurname(null);
        collaboratorCreationDto.setName(null);
        collaboratorCreationDto.setUsername(null);
        callControllerBadRequest();
        verifyNoInteractions(administratorService);

        collaboratorCreationDto.setSurname("");
        collaboratorCreationDto.setName("");
        collaboratorCreationDto.setUsername("");
        callControllerBadRequest();
        verifyNoInteractions(administratorService);
    }

    private void callControllerBadRequest() throws Exception{
        mockMvc.perform(post("/admins/collaborators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(collaboratorCreationDto))
            .principal(authentication))
            .andExpect(status().isBadRequest());
    }

    private void callControllerIsCreated() throws Exception{
        mockMvc.perform(post("/admins/collaborators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(collaboratorCreationDto))
            .principal(authentication))
            .andExpect(status().isCreated());
    }
}
