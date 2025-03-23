
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EstateLocationFeatures 
{
    private boolean isNearPark;
    private boolean isNearSchool;
    private boolean isNearPublicTransport;   
}
