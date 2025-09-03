
package com.dietiestates.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Address")
@Table(name = "address")
@Data
@NoArgsConstructor
@ToString(exclude = "realEstate")
public class Address 
{
	@Id
	private Long addressId;
	 
    @NotNull
	@Column(nullable = false, 
			updatable = true)
	private String state;

	@NotNull
	@Column(nullable = false, 
		 	updatable = true)
	private String country;

	@NotNull
	@Column(nullable = false,
		    updatable = true)
	private String city;

	@NotNull
	@Column(nullable = false, 
		    updatable = true)
	private String street;

	@NotNull
	@Column(name = "postal_code",
		 	nullable = false, 
		 	updatable = true,
		 	length = 6)
	private String postalCode;

	@NotNull
	@Column(name = "house_number",
		 	nullable = false, 
		 	updatable = true)
	private int houseNumber;

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
				foreignKey = @ForeignKey(name = "adress_to_real_estate_fk"))
	private RealEstate realEstate;



	public Address(String state, String country, String city, String street, String postalCode, int houseNumber, double longitude, double latitude) 
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