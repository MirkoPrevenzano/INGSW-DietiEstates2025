
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.validator.EmailValidator;
import com.dietiestates.backend.validator.PasswordValidator;
import com.dietiestates.backend.validator.VatNumberValidator;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyRegistrationDto
{
    @NotBlank
    private String agencyName;

    @NotBlank
    private String businessName;

    @NotBlank
    @VatNumberValidator
    private String vatNumber;
    
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