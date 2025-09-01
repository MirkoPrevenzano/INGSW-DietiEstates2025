
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Schema(description = "DTO per la creazione di un agente, contenente le informazioni anagrafiche e l'identificativo univoco (username).")
public class AgentCreationDto extends UserRegistrationDto
{
    public AgentCreationDto(String name, String surname, String username) 
    {
        super(name, surname, username);
    } 
}