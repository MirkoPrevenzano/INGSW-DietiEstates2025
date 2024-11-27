package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="indirizzo")
public class Indirizzo {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String regione;
     private String provincia;
     private String città;
     private String cap;
     private String via;

     public Indirizzo(){}

     @Column(name="numero_civico")
     private String numeroCivico;

     public String getCap() {
         return cap;
     }
     public void setCap(String cap) {
         this.cap = cap;
     }

     public String getCittà() {
         return città;
     }
     public void setCittà(String città) {
         this.città = città;
     }

     public Long getId() {
         return id;
     }
     public void setId(Long id) {
         this.id = id;
     }

     public String getNumeroCivico() {
         return numeroCivico;
     }
     public void setNumeroCivico(String numeroCivico) {
         this.numeroCivico = numeroCivico;
     }

     public String getProvincia() {
         return provincia;
     }
     public void setProvincia(String provincia) {
         this.provincia = provincia;
     }

     public String getRegione() {
         return regione;
     }
     public void setRegione(String regione) {
         this.regione = regione;
     }

     public String getVia() {
         return via;
     }
     public void setVia(String via) {
         this.via = via;
     }
   
	


}
