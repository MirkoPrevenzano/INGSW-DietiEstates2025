package com.example.datatier.model.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PropertyFeatures {

    @Column(columnDefinition = "boolean default false")
    private boolean elevator;
    @Column(columnDefinition = "boolean default false")
    private boolean concierge;
    @Column(name="air_conditioning", columnDefinition = "boolean default false")
    private boolean airConditioning;
    @Column(columnDefinition = "boolean default false")
    private boolean terrace;
    @Column(columnDefinition = "boolean default false")
    private boolean garage;
    @Column(columnDefinition = "boolean default false")
    private boolean balcony;
    @Column(columnDefinition = "boolean default false")
    private boolean garden;
    @Column(columnDefinition = "boolean default false")
    private boolean pool;

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public boolean isConcierge() {
        return concierge;
    }

    public void setConcierge(boolean concierge) {
        this.concierge = concierge;
    }

    public boolean isClimatizzatore() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public boolean isTerrace() {
        return terrace;
    }

    public void setTerrace(boolean terrace) {
        this.terrace = terrace;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public boolean isPool() {
        return pool;
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }
}