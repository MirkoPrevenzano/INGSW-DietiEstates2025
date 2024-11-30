package com.example.datatier.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;


@Entity
@Table(name = "property_stats")
public class PropertyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="visit_number", columnDefinition = "bigint default 0")
    private long visitNumber;

    @Column(name="offer_number", columnDefinition = "bigint default 0")
    private long offerNumber;
    
    @OneToOne
    @JoinColumn(name="id_property", referencedColumnName = "id", foreignKey = @ForeignKey(name="property_feature_fk"))
    private Property property;

    PropertyStats(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }
    public void setProperty(Property property) {
        this.property = property;
    }

    public long getOfferNumber() {
        return offerNumber;
    }
    public void setOfferNumber(long offerNumber) {
        this.offerNumber = offerNumber;
    }

    public long getVisitNumber() {
        return visitNumber;
    }
    public void SetVisitNumber(long visitNumber) {
        this.visitNumber = visitNumber;
    }


    
}
