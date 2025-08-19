
package com.dietiestates.backend.dto.request.support;

import com.dietiestates.backend.validator.groups.OnCreate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto 
{
    @NotBlank(groups = OnCreate.class)
    private String state;

    @NotBlank(groups = OnCreate.class)
    private String country;

    @NotBlank(groups = OnCreate.class)
    private String city;

    @NotBlank(groups = OnCreate.class)
    private String street;

    @NotBlank(groups = OnCreate.class)
    @Pattern(regexp = "^[0-9]{5}$", message = "must be of 5 digits")
    private String postalCode;

    @NotNull(groups = OnCreate.class)
    private String houseNumber;

    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;
}