
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;

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
    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5}$", message = "postal code must be of 5 digits")
    private String postalCode;

    @NotNull// TODO: cambiare in String
    private Integer houseNumber;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @NotNull
    @DecimalMin(value = ".90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;
}