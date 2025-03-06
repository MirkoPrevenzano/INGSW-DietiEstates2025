package com.example.datatier.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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
}
