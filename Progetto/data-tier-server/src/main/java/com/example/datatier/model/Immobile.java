package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
/*
 * Uso approccio Joined per rendere efficiente l'inserimento di diversi tipi di immobile.
 * Immobile ha associazione 1 a 1 con i vari tipi di post immobile così che fitto e vendita 
 * ereditano tutti gli attributi in comune con gli immobili.
 */
@Entity
@Table(name = "immobile")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descrizione;

    @Column(name = "dimensione_in_mq")
    private double dimensioneInMq;

    @Column(name = "numero_stanze")
    private int numeroStanze;

    @Column(name = "numero_piano")
    private int numeroPiano;

    @Column(name = "classe_energetica")
    private String classeEnergetica;

    @Column(name = "numero_posti_auto")
    private int numeroPostiAuto;

    

    @Column(name = "data_caricamento")
    private Timestamp dataCaricamento;

    @Embedded
    private CaratteristicheImmobile caratteristiche;

    @OneToOne
    @JoinColumn(name="id_indirizzo", referencedColumnName = "id")
    private Indirizzo indirizzo;

    @ManyToOne
    @JoinColumn(name="id_agente_immobiliare", referencedColumnName = "id")
    private AgenteImmobiliare agenteImmobiliare;

    public Immobile() {
    }

    public Long getId() {
        return id;
    }

    public CaratteristicheImmobile getCaratteristiche() {
        return caratteristiche;
    }

    public void setCaratteristiche(CaratteristicheImmobile caratteristiche) {
        this.caratteristiche = caratteristiche;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getDimensioneInMq() {
        return dimensioneInMq;
    }

    public void setDimensioneInMq(double dimensioneInMq) {
        this.dimensioneInMq = dimensioneInMq;
    }

    public int getNumeroStanze() {
        return numeroStanze;
    }

    public void setNumeroStanze(int numeroStanze) {
        this.numeroStanze = numeroStanze;
    }

    public int getNumeroPiano() {
        return numeroPiano;
    }

    public void setNumeroPiano(int numeroPiano) {
        this.numeroPiano = numeroPiano;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public int getNumeroPostiAuto() {
        return numeroPostiAuto;
    }

    public void setNumeroPostiAuto(int numeroPostiAuto) {
        this.numeroPostiAuto = numeroPostiAuto;
    }

    

    public Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public AgenteImmobiliare getAgenteImmobiliare() {
        return agenteImmobiliare;
    }
    public void setAgenteImmobiliare(AgenteImmobiliare agenteImmobiliare) {
        this.agenteImmobiliare = agenteImmobiliare;
    }
}