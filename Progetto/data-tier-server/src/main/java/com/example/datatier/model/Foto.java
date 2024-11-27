package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "foto")
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="chiave_s3")
    private String chiaveS3;

    @ManyToOne
    @JoinColumn(name=" id_immobile",referencedColumnName = "id")
    private Immobile immobile;

    public Foto(){}
    
    public String getChiaveS3() {
        return chiaveS3;
    }
    public void setChiaveS3(String chiaveS3) {
        this.chiaveS3 = chiaveS3;
    }

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
}
