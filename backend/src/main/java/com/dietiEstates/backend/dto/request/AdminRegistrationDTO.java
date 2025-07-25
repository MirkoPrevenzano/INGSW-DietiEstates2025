
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiEstates.backend.validator.EmailValidator;
import com.dietiEstates.backend.validator.PasswordValidator;
import com.dietiEstates.backend.validator.VatNumberValidator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegistrationDTO 
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

    @NotBlank
    private String agencyName;

    @NotBlank
    private String businessName;

    @NotBlank
    @VatNumberValidator
    private String vatNumber;
}