package com.example.datatier.dto;

import java.sql.Timestamp;

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

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public void setSizeInSquareMeters(double sizeInSquareMeters) {
        this.sizeInSquareMeters = sizeInSquareMeters;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    public int getNumberParkingSpace() {
        return numberParkingSpace;
    }

    public void setNumberParkingSpace(int numberParkingSpace) {
        this.numberParkingSpace = numberParkingSpace;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyAgent() {
        return propertyAgent;
    }

    public void setPropertyAgent(String propertyAgent) {
        this.propertyAgent = propertyAgent;
    }
}