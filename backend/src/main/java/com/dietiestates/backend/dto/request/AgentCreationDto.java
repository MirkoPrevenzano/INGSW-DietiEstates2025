
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationDto;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class AgentCreationDto extends UserRegistrationDto
{
    public AgentCreationDto(String name, String surname, String username) 
    {
        super(name, surname, username);
    } 
}