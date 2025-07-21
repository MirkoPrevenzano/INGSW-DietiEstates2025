
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;

import com.dietiEstates.backend.validator.groups.OnCreate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO 
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
    @Pattern(regexp = "^[0-9]{5}$", message = "postal code must be of 5 digits")
    private String postalCode;

    @NotNull(groups = OnCreate.class) // TODO: cambiare in String
    private Integer houseNumber;

    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = ".90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;
}