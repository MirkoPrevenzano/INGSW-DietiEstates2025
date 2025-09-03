
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationWithPasswordDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@Schema(description = "DTO per la registrazione di un cliente, contenente i dati anagrafici, l'identificativo univoco (username) e la password.")
public class CustomerRegistrationDto extends UserRegistrationWithPasswordDto
{
    public CustomerRegistrationDto(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    } 
}