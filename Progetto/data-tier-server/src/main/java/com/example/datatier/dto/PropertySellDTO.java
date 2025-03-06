package com.example.datatier.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertySellDTO extends PropertyDTO {
    private double price;
    private LocalDate availabilityDate;
    private String propertyState;
}