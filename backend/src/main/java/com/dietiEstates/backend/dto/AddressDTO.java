
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO 
{
    @NonNull
    private String state;

    @NonNull
    private String country;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String postalCode;

    @NonNull
    private Integer houseNumber;

    @NonNull
    private Double longitude;

    @NonNull
    private Double latitude;
}