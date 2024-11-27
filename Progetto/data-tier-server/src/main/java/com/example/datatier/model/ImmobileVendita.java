package com.example.datatier.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="vendita")
@PrimaryKeyJoinColumn(name="id")
public class ImmobileVendita extends Immobile {
    
    private double prezzo;

    @Column(name="data_disponibilità")
    private Date dataDisponibilita;

    @Column(name="stato_proprietà")
    private String statoProprietà;

    public ImmobileVendita(){}

    
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Date getDataDisponibilita() {
        return dataDisponibilita;
    }
    public void setDataDisponibilita(Date dataDisponibilita) {
        this.dataDisponibilita = dataDisponibilita;
    }

    public String getStatoProprietà() {
        return statoProprietà;
    }
    public void setStatoProprietà(String statoProprietà) {
        this.statoProprietà = statoProprietà;
    }
}
