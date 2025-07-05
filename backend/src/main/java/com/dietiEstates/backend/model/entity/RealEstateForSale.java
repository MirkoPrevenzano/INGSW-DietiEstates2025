
package com.dietiEstates.backend.model.entity;

import java.time.LocalDateTime;

import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;



@Entity(name = "RealEstateForSale")
@Table(name = "real_estate_for_sale")
@PrimaryKeyJoinColumn(name = "real_estate_for_sale_id", 
                      foreignKey = @ForeignKey(name = "real_estate_for_sale_to_real_estate_fk"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RealEstateForSale extends RealEstate 
{
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "notary_deed_state",
            nullable = false, 
            updatable = true)    
    private NotaryDeedState notaryDeedState;


    
    public RealEstateForSale(String title, String description, LocalDateTime uploadingDate, Double price, Double condoFee, EnergyClass energyClass,
                             InternalRealEstateFeatures internalFeatures, ExternalRealEstateFeatures externalFeatures,
                             @NonNull NotaryDeedState notaryDeedState)
    {
        super(title,description, uploadingDate, price, condoFee, energyClass, internalFeatures, externalFeatures);
        this.notaryDeedState = notaryDeedState;
    }
}