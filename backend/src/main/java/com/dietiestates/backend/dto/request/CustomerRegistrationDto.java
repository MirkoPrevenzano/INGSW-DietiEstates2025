
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.EmailValidator;
import com.dietiestates.backend.validator.PasswordValidator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDto 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @EmailValidator
    private String username;

    @NotBlank
    @PasswordValidator
    private String password;
}