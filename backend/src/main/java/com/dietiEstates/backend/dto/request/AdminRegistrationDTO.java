
package com.dietiEstates.backend.dto.request;

import com.dietiEstates.backend.validator.EmailValidator;
import com.dietiEstates.backend.validator.VatNumberValidator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



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
    private String password;

    @NotBlank
    private String agencyName;

    @NotBlank
    private String businessName;

    @NotBlank
    @VatNumberValidator
    private String vatNumber;
}