
package com.dietiEstates.backend.extra;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateBooleanFeaturesDTO 
{
    @NonNull
    private Boolean airConditioning;

    @NonNull
    private Boolean heating;  

    @NonNull
    private Boolean elevator;

    @NonNull
    private Boolean concierge;

    @NonNull
    private Boolean terrace;

    @NonNull
    private Boolean garage;

    @NonNull
    private Boolean balcony;

    @NonNull
    private Boolean garden;

    @NonNull
    private Boolean swimmingPool;
}
