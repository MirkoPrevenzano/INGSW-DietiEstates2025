
package com.dietiEstates.backend.model.entity;


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
		 updatable = true)
	 private String postalCode;

	 @NonNull
	 @Column(name = "house_number",
		 nullable = false, 
		 updatable = true)
	 private Integer houseNumber;

	 @Column(nullable = false, 
		 updatable = true)
	 private double longitude;

	 @Column(nullable = false, 
		 updatable = true)
	 private double latitude;

	 
	 @MapsId
	 @OneToOne(fetch = FetchType.LAZY, 
		   cascade = {},
		   orphanRemoval = false)
	 @JoinColumn(name = "real_estate_id",
		 nullable = false,
		 updatable = true,
		 foreignKey = @ForeignKey(name = "real_estate_to_address_fk"))
	 private RealEstate realEstate;


	public Address(@NonNull String state, @NonNull String country, @NonNull String city, @NonNull String street,
				   @NonNull String postalCode, @NonNull Integer houseNumber, double longitude, double latitude) 
	{
		this.state = state;
		this.country = country;
		this.city = city;
		this.street = street;
		this.postalCode = postalCode;
		this.houseNumber = houseNumber;
		this.longitude = longitude;
		this.latitude = latitude;
	 }
}