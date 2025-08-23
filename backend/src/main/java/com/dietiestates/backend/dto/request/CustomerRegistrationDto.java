
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationWithPasswordDto;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CustomerRegistrationDto extends UserRegistrationWithPasswordDto
{
    public CustomerRegistrationDto(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    } 
}