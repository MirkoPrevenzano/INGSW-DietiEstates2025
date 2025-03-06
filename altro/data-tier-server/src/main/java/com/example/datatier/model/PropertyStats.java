package com.example.datatier.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.OneToOne;


@Data
@NoArgsConstructor
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
    
    //riferimento a property, unidirezionale
    @OneToOne
    @JoinColumn(name="id_property", referencedColumnName = "id", foreignKey = @ForeignKey(name="property_feature_fk"))
    private Property property;
}
