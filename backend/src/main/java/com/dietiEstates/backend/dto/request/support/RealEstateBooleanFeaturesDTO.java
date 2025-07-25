
package com.dietiEstates.backend.dto.request.support;

import jakarta.validation.constraints.NotNull;

import com.dietiEstates.backend.validator.groups.OnCreate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateBooleanFeaturesDTO 
{
    @NotNull(groups = OnCreate.class)
    private Boolean airConditioning;

    @NotNull(groups = OnCreate.class)
    private Boolean heating;  

    @NotNull(groups = OnCreate.class)
    private Boolean elevator;

    @NotNull(groups = OnCreate.class)
    private Boolean concierge;

    @NotNull(groups = OnCreate.class)
    private Boolean terrace;

    @NotNull(groups = OnCreate.class)
    private Boolean garage;

    @NotNull(groups = OnCreate.class)
    private Boolean balcony;

    @NotNull(groups = OnCreate.class)
    private Boolean garden;

    @NotNull(groups = OnCreate.class)
    private Boolean swimmingPool;
}