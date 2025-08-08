
package com.dietiEstates.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Agency")
@Table(name = "agency", 
       uniqueConstraints = @UniqueConstraint(name = "agent_uk", columnNames = {"businessName", "vatNumber"}))
@Data
@NoArgsConstructor
@ToString(exclude = "administrator")
public class Agency 
{
    @Id
    private Long agencyId;
    
    @NotNull
    @Column(name = "agency_name",
            nullable = false, 
            updatable = true)
    private String agencyName;

    @NotNull
    @Column(name = "business_name",
            nullable = false, 
            updatable = true)
    private String businessName;
    
    @NotNull
    @Column(name = "vat_number",
            nullable = false, 
            updatable = true,
            length = 12)
    private String vatNumber;

    

    public Agency(String agencyName, String businessName, String vatNumber) {
        this.agencyName = agencyName;
        this.businessName = businessName;
        this.vatNumber = vatNumber;
}




    // TODO: mettere relazione ONE TO MANY
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,
              cascade = {},
              orphanRemoval = false)
    @JoinColumn(name = "admin_id",
                nullable = false,
                updatable = true,
                foreignKey = @ForeignKey(name = "agency_to_admin_fk"))
    private Administrator administrator;
}