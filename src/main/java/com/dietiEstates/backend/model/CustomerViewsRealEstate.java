
package com.dietiEstates.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;


@Entity(name = "CustomerViewsRealEstate")
@Table(name = "customer_view_real_estate")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CustomerViewsRealEstate 
{
    @EmbeddedId
    @NonNull
    private CustomerViewsRealEstateId customerViewsRealEstateId;
        
    @NonNull    
    @Column(name = "viewed_date", 
            nullable = false, 
            updatable = true)
    private LocalDateTime viewDate;



    @NonNull
    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.EAGER,
               cascade = {})
    @JoinColumn(name = "customer_id", 
                nullable = false, 
                updatable = true,
                foreignKey = @ForeignKey(name = "customer_view_real_estate_fk"))
    private Customer customer;

    @NonNull
    @MapsId("realEstateId")
    @ManyToOne(fetch = FetchType.EAGER,
               cascade = {})
    @JoinColumn(name = "real_estate_id", 
                nullable = false, 
                updatable = true,
                foreignKey = @ForeignKey(name = "real_estate_is_viewed_by_customer_fk"))
    private RealEstate realEstate;
}

