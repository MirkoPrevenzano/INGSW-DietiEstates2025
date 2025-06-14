
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AgentRegistrationDTO 
{
    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String username;

    @NonNull
    private String password;
}