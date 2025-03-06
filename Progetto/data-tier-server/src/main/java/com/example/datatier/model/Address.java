package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="address")
@Data
@NoArgsConstructor
public class Address {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column(nullable = false)
     private String region;
     @Column(nullable = false)
     private String province;
     @Column(nullable = false)
     private String city;
     @Column(nullable = false)
     private String cap;
     @Column(nullable = false)
     private String street;
     @Column(name="house_number", nullable = false)
     private int houseNumber;

     /*Riferimento immobile */
}
