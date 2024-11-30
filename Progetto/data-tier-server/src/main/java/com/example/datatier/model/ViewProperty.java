package com.example.datatier.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.datatier.model.embeddable.ViewPropertyId;

@Entity
@Table(name = "view_property")
public class ViewProperty {

    @EmbeddedId
    private ViewPropertyId id;

    @ManyToOne
    @MapsId("idCustomer")
    @JoinColumn(name = "id_customer", nullable = false, foreignKey = @ForeignKey(name="customer_view_property_fk"))
    private Customer customer;

    @ManyToOne
    @MapsId("idProperty")
    @JoinColumn(name = "id_property", nullable=false, foreignKey = @ForeignKey(name="view_property_fk"))
    private Property property;

    @Column(name = "last_visit_date", nullable = false)
    private LocalDate lastVisitDate;

    public ViewProperty() {}

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }
    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public ViewPropertyId getId() {
        return id;
    }
    public void setId(ViewPropertyId id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }
    public void setImmobile(Property property) {
        this.property = property;
    }

}

