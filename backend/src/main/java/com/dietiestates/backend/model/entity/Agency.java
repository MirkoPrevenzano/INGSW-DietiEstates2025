
package com.dietiestates.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Agency")
@Table(name = "agency", 
       uniqueConstraints = @UniqueConstraint(name = "agency_uk", columnNames = {"businessName", "vatNumber"}))
@Data
@NoArgsConstructor
@ToString(exclude = "administrators")
public class Agency 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    
    @OneToMany(mappedBy = "agency",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.ALL},
               orphanRemoval = true)
    private List<Administrator> administrators = new ArrayList<>();



    public Agency(String agencyName, String businessName, String vatNumber) 
    {
        this.agencyName = agencyName;
        this.businessName = businessName;
        this.vatNumber = vatNumber;
    }



    public void addAdministrator(Administrator newAdministrator)
    {
        this.administrators.add(newAdministrator);
        newAdministrator.setAgency(this);
    }

    
    public void removeAdministrator(Administrator adminToRemove)
    {
        this.administrators.remove(adminToRemove);
        adminToRemove.setAgency(null);
    }
}