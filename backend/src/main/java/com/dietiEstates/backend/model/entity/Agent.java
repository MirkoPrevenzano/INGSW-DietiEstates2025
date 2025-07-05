
package com.dietiEstates.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.dietiEstates.backend.model.embeddable.AgentStats;

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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;



@Entity(name = "Agent")
@Table(name = "agent", 
       uniqueConstraints = @UniqueConstraint(name = "agent_uk", columnNames = "username"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true, 
          exclude = "realEstates")
/* @AttributeOverride(name = "userId", 
                   column = @Column(name = "agent_id")) */
public class Agent extends User
{
/*         @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; */

    @Column(name = "must_change_password", 
            nullable = false, 
            updatable = true)
    private boolean mustChangePassword;

    @Embedded
    AgentStats agentStats = new AgentStats();



    @ManyToOne(fetch = FetchType.LAZY,
               cascade = {})
    @JoinColumn(name = "admin_id", 
                nullable = false, 
                updatable = true,
                foreignKey = @ForeignKey(name = "agent_to_admin_fk"))
    private Administrator administrator;

    @OneToMany(mappedBy = "agent", 
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<RealEstate> realEstates = new ArrayList<>();



    public Agent(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    }
    

    
    public void addRealEstate(RealEstate newRealEstate) 
    {
       this.realEstates.add(newRealEstate);
       newRealEstate.setAgent(this);
    }  
    
    public void removeRealEstate(RealEstate realEstateToRemove)
    {
        this.realEstates.remove(realEstateToRemove);
        realEstateToRemove.setAgent(null);
    }
}