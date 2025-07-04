
package com.dietiEstates.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentPublicInfoDTO 
{
    private String name;
    private String surname;
    private String username;
}