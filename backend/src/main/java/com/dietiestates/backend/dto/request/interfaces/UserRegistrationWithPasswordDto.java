
package com.dietiestates.backend.dto.request.interfaces;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.PasswordValidator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class UserRegistrationWithPasswordDto extends UserRegistrationDto 
{
    @NotBlank
    @PasswordValidator
    private String password;

    protected UserRegistrationWithPasswordDto(String name, String surname, String username, String password) 
    {
        super(name, surname, username);
        this.password = password;
    } 
}