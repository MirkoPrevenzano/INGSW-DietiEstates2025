package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name="property_rent")
@PrimaryKeyJoinColumn(name="id")
public class PropertyRent extends Property{

    @Column(name="monthly_rent", nullable = false)
    private double monthlyRent;
    @Column(name="security_deposit", nullable = false)
    private double securityDeposit;
    @Column(name="contract_years", columnDefinition="integer default 1")
    private int contractYears;
    @Column(name="condo_fee", nullable = false)
    private double condoFee;
}
