package com.example.datatier.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.example.datatier.model.embeddable.ViewPropertyId;

@Data
@NoArgsConstructor
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
}

