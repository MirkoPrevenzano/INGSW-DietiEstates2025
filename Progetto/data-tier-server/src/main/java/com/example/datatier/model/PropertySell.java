package com.example.datatier.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="property_sell")
@PrimaryKeyJoinColumn(name="id")
public class PropertySell extends Property {
    
    @Column(nullable = false)
    private double price;

    @Column(name="availability_date", nullable = false)
    private LocalDate availabilityDate;

    @Column(name="property_state",nullable = false)
    private String propertyState;

    public PropertySell(){}

    
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }
    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getPropertyState() {
        return propertyState;
    }
    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }
}
