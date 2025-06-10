
package com.dietiEstates.backend.model.entity;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.RealEstateStats;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;



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

    @Column(nullable = false, 
            updatable = true)
    private String title;

    @Column(nullable = false, 
            updatable = true, 
            columnDefinition = "text")
    private String description;

    @Column(name = "uploading_date",
            nullable = true, 
            updatable = false)    
    private LocalDateTime uploadingDate;
    
    @Column(nullable = false, 
            updatable = true)
    private Double price;

    @Column(name = "condo_fee",
            nullable = false, 
            updatable = true)         
    private Double condoFee;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "energy_class",
            nullable = false, 
            updatable = true)         
    private EnergyClass energyClass;

    @Embedded 
    private InternalRealEstateFeatures internalFeatures;

    @Embedded
    private ExternalRealEstateFeatures externalFeatures;    
    
    @Embedded
    private RealEstateStats realEstateStats = new RealEstateStats();



    @OneToOne(mappedBy = "realEstate",
              fetch = FetchType.EAGER, 
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, 
              orphanRemoval = true)
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER,
               cascade = {})
    @JoinColumn(name = "real_estate_agent_id", 
                nullable = false, 
                updatable = true, 
                foreignKey = @ForeignKey(name = "real_estate_to_real_estate_agent_fk"))
    private RealEstateAgent realEstateAgent;

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



    public RealEstate(String title, String description, LocalDateTime uploadingDate, Double price, Double condoFee, EnergyClass energyClass,
                      InternalRealEstateFeatures internalFeatures, ExternalRealEstateFeatures externalFeatures)
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