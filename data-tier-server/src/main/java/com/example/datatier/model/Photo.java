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

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="key_s3", nullable = false)
    private String keyS3;

    @ManyToOne
    @JoinColumn(name=" id_property",
        referencedColumnName = "id", 
        nullable = false,
        foreignKey = @ForeignKey(name="property_photo_fk"))
    private Property property;

    public Photo(){}
    
    public String getKeyS3() {
        return keyS3;
    }
    public void setKeyS3(String keyS3) {
        this.keyS3 = keyS3;
    }

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
}
