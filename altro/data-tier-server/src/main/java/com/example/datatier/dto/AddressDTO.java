package com.example.datatier.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String city;
    private String region;
    private String cap;
    private String province;
    private int houseNumber;    
}