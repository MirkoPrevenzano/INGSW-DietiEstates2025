package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

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

    public PropertyRent(){}

    public double getMonthlyRent() {
        return monthlyRent;
    }
    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
    
    public double getDepositoCauzionale() {
        return securityDeposit;
    }
    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public int getContractYears() {
        return contractYears;
    }
    public void setContractYears(int contractYears) {
        this.contractYears = contractYears;
    }

    public double getCondoFee() {
        return condoFee;
    }
    public void setCondoFee(double condoFee) {
        this.condoFee = condoFee;
    }


}
