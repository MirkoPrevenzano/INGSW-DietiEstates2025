
package com.dietiEstates.backend.extra;

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
