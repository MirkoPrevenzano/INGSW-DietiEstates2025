
package com.dietiEstates.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.dietiEstates.backend.model.embeddable.RealEstateAgentStats;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ForeignKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "RealEstateAgent")
@Table(name = "real_estate_agent", 
       uniqueConstraints = @UniqueConstraint(name = "real_estate_agent_uk", columnNames = "username"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true, 
          exclude = "realEstates")
@AttributeOverride(name = "userId", 
                   column = @Column(name = "real_estate_agent_id"))
public class RealEstateAgent extends User 
{
    @Embedded
    RealEstateAgentStats realEstateAgentStats = new RealEstateAgentStats();



    @ManyToOne(fetch = FetchType.EAGER,
               cascade = {})
    @JoinColumn(name = "admin_id", 
                nullable = false, 
                updatable = false,
                foreignKey = @ForeignKey(name = "real_estate_agent_to_admin_fk"))
    private Administrator administrator;

    @OneToMany(mappedBy = "realEstateAgent", 
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<RealEstate> realEstates = new ArrayList<>();



    public RealEstateAgent(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    }
    

    
    public void addRealEstate(RealEstate newRealEstate) 
    {
       this.realEstates.add(newRealEstate);
       newRealEstate.setRealEstateAgent(this);
    }  
    
    public void removeRealEstate(RealEstate realEstateToRemove)
    {
        this.realEstates.remove(realEstateToRemove);
        realEstateToRemove.setRealEstateAgent(null);
    }
}