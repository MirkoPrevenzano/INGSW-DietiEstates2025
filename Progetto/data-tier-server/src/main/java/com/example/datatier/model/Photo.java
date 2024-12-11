package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="key_s3", nullable = false)
    private String keyS3;

    //riferimento alla property associata
    @ManyToOne
    @JoinColumn(name=" id_property",
        referencedColumnName = "id", 
        nullable = false,
        foreignKey = @ForeignKey(name="property_photo_fk"))
    private Property property;

    
}
