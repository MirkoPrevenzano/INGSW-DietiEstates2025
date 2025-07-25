
package com.dietiEstates.backend.dto.response.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentPublicInfoDTO 
{
    private String name;
    private String surname;
    private String username;
    private String agencyName;
}