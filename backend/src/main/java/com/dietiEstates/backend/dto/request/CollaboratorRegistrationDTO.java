
package com.dietiEstates.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorRegistrationDTO 
{
    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String username;
}