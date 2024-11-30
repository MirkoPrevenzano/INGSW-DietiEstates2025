package com.example.datatier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.example.datatier.model.embeddable.PropertyFeatures;

/*
 * Uso approccio Joined per rendere efficiente l'inserimento di diversi tipi di immobile.
 * Immobile ha associazione 1 a 1 con i vari tipi di post immobile così che fitto e vendita 
 * ereditano tutti gli attributi in comune con gli immobili.
 */
@Entity
@Table(name = "property")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "size_in_square_meters" ,nullable=false)
    private double sizeInSquareMeters;

    @Column(name = "room_count",nullable=false)
    private int roomCount;

    @Column(name = "floor_number",nullable=false)
    private int floorNumber;

    @Column(name = "energy_class",nullable=false)
    private String energyClass;

    @Column(name = "number_parking_space",nullable=false)
    private int numberParkingSpace;

    

    @Column(name = "upload_date", nullable = false, updatable = false)
    private Timestamp uploadDate;

    @Embedded
    private PropertyFeatures features;

    @OneToOne
    @JoinColumn(name="id_address", 
        referencedColumnName = "id", 
        nullable = false, 
        foreignKey = @ForeignKey(name="property_address_fk"))
    private Address address;


    @ManyToOne
    @JoinColumn(name="id_property_agent", 
        referencedColumnName = "id", 
        nullable = false, 
        foreignKey = @ForeignKey(name="property_agent_fk"))
    private PropertyAgent propertyAgent;

    @OneToMany(mappedBy = "property")
    private List<ViewProperty> visualizzaImmobili;

    public Property() {
    }

    @PrePersist
    protected void onCreate() {
        this.uploadDate = Timestamp.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public PropertyFeatures getFeatures() {
        return features;
    }

    public void setFeaturers(PropertyFeatures features) {
        this.features = features;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public void setSizeInSquareMeters(double sizeInSquareMeters) {
        this.sizeInSquareMeters = sizeInSquareMeters;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    public int getNumberParkingSpace() {
        return numberParkingSpace;
    }

    public void setNumberParkingSpace(int numberParkingSpace) {
        this.numberParkingSpace = numberParkingSpace;
    }

    

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public PropertyAgent getPropertyAgent() {
        return propertyAgent;
    }
    public void setPropertyAgent(PropertyAgent propertyAgent) {
        this.propertyAgent = propertyAgent;
    }

    
}