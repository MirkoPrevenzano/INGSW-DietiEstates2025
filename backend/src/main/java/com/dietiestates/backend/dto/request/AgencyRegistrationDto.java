
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationWithPasswordDto;
import com.dietiestates.backend.validator.VatNumberValidator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AgencyRegistrationDto extends UserRegistrationWithPasswordDto
{
    @NotBlank
    private String agencyName;

    @NotBlank
    private String businessName;

    @NotBlank
    @VatNumberValidator
    private String vatNumber;

    
    public AgencyRegistrationDto(String name, String surname, String username, String password, String agencyName, String businessName, String vatNumber) 
    {
        super(name, surname, username, password);
        this.agencyName = agencyName;
        this.businessName = businessName;
        this.vatNumber = vatNumber;
    }
}