
package com.dietiestates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentPublicInfoDto 
{
    private String name;

    private String surname;

    private String username;
    
    private String agencyName;
}