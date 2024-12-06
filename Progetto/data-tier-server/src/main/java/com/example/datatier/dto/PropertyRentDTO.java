package com.example.datatier.dto;

public class PropertyRentDTO extends PropertyDTO {
    private double monthlyRent;
    private double securityDeposit;
    private int contractYears;
    private double condoFee;

    // Getters and setters
    public double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public double getSecurityDeposit() {
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