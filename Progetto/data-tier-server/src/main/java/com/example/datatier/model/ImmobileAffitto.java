package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="affitto")
@PrimaryKeyJoinColumn(name="id")
public class ImmobileAffitto extends Immobile{

    @Column(name="canone_mensile")
    private double canoneMensile;
    @Column(name="deposito_cauzionale")
    private double depositoCauzionale;
    @Column(name="durata_contratto_anni")
    private int durataContrattoAnni;
    @Column(name="spesa_condominiale")
    private double spesaCondominiale;

    public ImmobileAffitto(){}

    public double getCanoneMensile() {
        return canoneMensile;
    }
    public void setCanoneMensile(double canoneMensile) {
        this.canoneMensile = canoneMensile;
    }
    
    public double getDepositoCauzionale() {
        return depositoCauzionale;
    }
    public void setDepositoCauzionale(double depositoCauzionale) {
        this.depositoCauzionale = depositoCauzionale;
    }

    public int getDurataContrattoAnni() {
        return durataContrattoAnni;
    }
    public void setDurataContrattoAnni(int durataContrattoAnni) {
        this.durataContrattoAnni = durataContrattoAnni;
    }

    public double getSpesaCondominiale() {
        return spesaCondominiale;
    }
    public void setSpesaCondominiale(double spesaCondominiale) {
        this.spesaCondominiale = spesaCondominiale;
    }


}
