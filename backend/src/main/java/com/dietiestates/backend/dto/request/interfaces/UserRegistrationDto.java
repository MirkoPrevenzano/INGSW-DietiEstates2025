
package com.dietiestates.backend.dto.request.interfaces;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.EmailValidator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserRegistrationDto 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @EmailValidator
    private String username;
}