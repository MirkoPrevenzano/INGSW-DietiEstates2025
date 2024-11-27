package com.example.datatier.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class CaratteristicheImmobile {

    private boolean ascensore;
    private boolean portineria;
    private boolean climatizzatore;
    private boolean terrazza;
    private boolean garage;
    private boolean balcone;
    private boolean giardino;
    private boolean piscina;

    public boolean isAscensore() {
        return ascensore;
    }

    public void setAscensore(boolean ascensore) {
        this.ascensore = ascensore;
    }

    public boolean isPortineria() {
        return portineria;
    }

    public void setPortineria(boolean portineria) {
        this.portineria = portineria;
    }

    public boolean isClimatizzatore() {
        return climatizzatore;
    }

    public void setClimatizzatore(boolean climatizzatore) {
        this.climatizzatore = climatizzatore;
    }

    public boolean isTerrazza() {
        return terrazza;
    }

    public void setTerrazza(boolean terrazza) {
        this.terrazza = terrazza;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isBalcone() {
        return balcone;
    }

    public void setBalcone(boolean balcone) {
        this.balcone = balcone;
    }

    public boolean isGiardino() {
        return giardino;
    }

    public void setGiardino(boolean giardino) {
        this.giardino = giardino;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }
}