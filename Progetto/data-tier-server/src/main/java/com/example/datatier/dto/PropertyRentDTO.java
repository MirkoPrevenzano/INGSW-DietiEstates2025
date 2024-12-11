package com.example.datatier.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyRentDTO extends PropertyDTO {
    private double monthlyRent;
    private double securityDeposit;
    private int contractYears;
    private double condoFee;
}