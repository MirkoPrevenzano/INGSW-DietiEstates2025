
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;



@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true)
public class AgentCustomerRegistrationDTO extends AdminRegistrationDTO
{
    @NonNull
    private String password;



    public AgentCustomerRegistrationDTO(String name, String surname, String username, String password)
    {
        super(name, surname, password);
        this.password = password;
    }
}