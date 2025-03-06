package com.example.datatier.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public abstract class PropertyDTO {
    private Long id;
    private String description;
    private double sizeInSquareMeters;
    private int roomCount;
    private int floorNumber;
    private String energyClass;
    private int numberParkingSpace;
    private Timestamp uploadDate;
    private String address;
    private String propertyAgent;
}