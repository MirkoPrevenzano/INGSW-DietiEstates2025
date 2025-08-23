
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationDto;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CollaboratorCreationDto extends UserRegistrationDto
{
    public CollaboratorCreationDto(String name, String surname, String username) 
    {
        super(name, surname, username);
    } }