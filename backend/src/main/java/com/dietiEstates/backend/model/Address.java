
package com.dietiEstates.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;



@Entity(name = "Address")
@Table(name = "address")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "realEstate")
public class Address 
{
     @Id
     private Long addressId;
     
     @NonNull
     @Column(nullable = false, 
             updatable = true)
     private String state;

     @NonNull
     @Column(nullable = false, 
             updatable = true)
     private String country;

     @NonNull
     @Column(nullable = false,
             updatable = true)
     private String city;

     @NonNull
     @Column(nullable = false, 
             updatable = true)
     private String street;

     @NonNull
     @Column(name = "postal_code",
             nullable = false, 
             updatable = true, 
             columnDefinition = "char(6)")
     private String postalCode;

     @NonNull
     @Column(name = "house_number",
             nullable = false, 
             updatable = true)
     private Integer houseNumber;

     @NonNull
     @Column(nullable = false, 
             updatable = true)
     private Double longitude;

     @NonNull
     @Column(nullable = false, 
             updatable = true)
     private Double latitude;


     
     @MapsId
     @OneToOne(fetch = FetchType.EAGER, 
               cascade = {},
               orphanRemoval = false)
     @JoinColumn(name = "real_estate_id",
                 nullable = false,
                 updatable = true,
                 foreignKey = @ForeignKey(name = "real_estate_to_address_fk"))
     private RealEstate realEstate;
}