
package com.dietiEstates.backend.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ForeignKey;

import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.RealEstateStats;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "RealEstate")
@Table(name = "real_estate")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@ToString(exclude = {"photos", "customerViewsRealEstates"})
public class RealEstate 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_estate_id")
    private Long realEstateId;

    @NotNull
    @Column(nullable = false, 
            updatable = true)
    private String title;

    @NotNull
    @Lob
    @Column(nullable = false, 
            updatable = true)
    private String description;

    @NotNull
    @Column(name = "uploading_date",
            nullable = false, 
            updatable = false)    
    private LocalDateTime uploadingDate;
    
    @NotNull
    @Column(nullable = false, 
            updatable = true)
    private Double price;

    @NotNull
    @Column(name = "condo_fee",
            nullable = false, 
            updatable = true)         
    private Double condoFee;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "energy_class",
            nullable = false, 
            updatable = true)         
    private EnergyClass energyClass;

    @NotNull
    @Embedded 
    private InternalRealEstateFeatures internalFeatures;

    @NotNull
    @Embedded
    private ExternalRealEstateFeatures externalFeatures;    
    
    @Embedded
    private RealEstateStats realEstateStats = new RealEstateStats();



    @OneToOne(mappedBy = "realEstate",
              fetch = FetchType.LAZY, 
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, 
              orphanRemoval = true,
              optional = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,
               cascade = {})
    @JoinColumn(name = "agent_id", 
                nullable = false, 
                updatable = true, 
                foreignKey = @ForeignKey(name = "real_estate_to_agent_fk"))
    private Agent agent;

    @OneToMany(fetch = FetchType.LAZY, 
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    @JoinColumn(name = "real_estate_id", 
                nullable = false, 
                updatable = true, 
                foreignKey = @ForeignKey(name = "photo_to_real_estate_fk"))
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "realEstate",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.REMOVE}, 
               orphanRemoval = true)
    private List<CustomerViewsRealEstate> customerViewsRealEstates = new ArrayList<>();



    public RealEstate(String title, String description, LocalDateTime uploadingDate, double price, double condoFee,
                     EnergyClass energyClass, InternalRealEstateFeatures internalFeatures, ExternalRealEstateFeatures externalFeatures)
    {
        this.title = title;
        this.description = description;
        this.uploadingDate = uploadingDate;
        this.price = price;
        this.condoFee = condoFee;
        this.energyClass = energyClass;
        this.internalFeatures = internalFeatures;
        this.externalFeatures = externalFeatures;
    }


    
    public void setAddress(Address newAddress) 
    {
        if (newAddress == null) 
            this.address.setRealEstate(null);
        else 
			newAddress.setRealEstate(this);

        this.address = newAddress;
    }

	
    public void addAddress(Address newAddress) 
    {
        this.setAddress(newAddress);
        newAddress.setRealEstate(this);
    }    
    
    public void removeAddress() 
    {
        this.getAddress().setRealEstate(null);
        this.setAddress(null);
    }
}