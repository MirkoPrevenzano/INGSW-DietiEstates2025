package com.example.datatier.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;


@Entity
@Table(name = "statistiche")
public class StatisticheImmobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="numero_visite")
    private long nVisite;

    @Column(name="numero_offerte")
    private long nOfferte;
    
    @OneToOne
    @JoinColumn(name="id_immobile", referencedColumnName = "id")
    private Immobile immobile;

    StatisticheImmobile(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Immobile getImmobile() {
        return immobile;
    }
    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public long getnOfferte() {
        return nOfferte;
    }
    public void setnOfferte(long nOfferte) {
        this.nOfferte = nOfferte;
    }

    public long getnVisite() {
        return nVisite;
    }
    public void setnVisite(long nVisite) {
        this.nVisite = nVisite;
    }


    
}
