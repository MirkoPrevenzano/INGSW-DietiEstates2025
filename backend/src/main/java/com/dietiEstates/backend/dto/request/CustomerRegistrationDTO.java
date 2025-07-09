
package com.dietiEstates.backend.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.dietiEstates.backend.validator.PasswordValidator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDTO 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @NotBlank
    @PasswordValidator
    private String password;
}