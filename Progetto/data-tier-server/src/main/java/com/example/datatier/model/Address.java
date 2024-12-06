package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="address")
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

     

     public Address(){/*Empty */}

  
     public String getCap() {
         return cap;
     }
     public void setCap(String cap) {
         this.cap = cap;
     }

     public String getCity() {
         return city;
     }
     public void setCity(String city) {
         this.city = city;
     }

     public Long getId() {
         return id;
     }
     public void setId(Long id) {
         this.id = id;
     }

     public int getHouseNumber() {
         return houseNumber;
     }
    

     public String getProvince() {
         return province;
     }
     public void setProvince(String province) {
         this.province = province;
     }

     public String getRegion() {
         return region;
     }
     public void setRegion(String region) {
         this.region = region;
     }

     public String getStreet() {
         return street;
     }
    

     public void setStreet(String street) {
         this.street = street;
     }
     public void setHouseNumber(int houseNumber) {
         this.houseNumber = houseNumber;
     }
   
    
    
	


}
