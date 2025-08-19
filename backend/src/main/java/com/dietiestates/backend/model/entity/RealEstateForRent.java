
package com.dietiestates.backend.model.entity;

import java.time.LocalDateTime;

import com.dietiestates.backend.enums.EnergyClass;
import com.dietiestates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiestates.backend.model.embeddable.InternalRealEstateFeatures;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity(name = "RealEstateForRent")
@Table(name = "real_estate_for_rent")
@PrimaryKeyJoinColumn(name = "real_estate_for_rent_id",
                      foreignKey = @ForeignKey(name = "real_estate_for_rent_to_real_estate_fk"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RealEstateForRent extends RealEstate
{
    @Column(name = "security_deposit",
            nullable = false, 
            updatable = true)
    private double securityDeposit;

    @Column(name = "contract_years",
            nullable = false, 
            updatable = true)
    private int contractYears;



    public RealEstateForRent(String title, String description, LocalDateTime uploadingDate, Double price, Double condoFee, EnergyClass energyClass,
                             InternalRealEstateFeatures internalFeatures, ExternalRealEstateFeatures externalFeatures,
                             Double securityDeposit, Integer contractYears)
    {
        super(title,description, uploadingDate, price, condoFee, energyClass, internalFeatures, externalFeatures);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}