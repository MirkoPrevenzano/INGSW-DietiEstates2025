
package com.dietiEstates.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity(name = "Administrator")
@Table(name = "administrator",
       uniqueConstraints = @UniqueConstraint(name = "administrator_uk", columnNames = "username"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true, 
          exclude = {"collaborators", "agents"})
/* @AttributeOverride(name = "userId", 
                   column = @Column(name = "admin_id")) */
public class Administrator extends User
{
/*         @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; */

    @Column(name = "must_change_password", 
            nullable = false, 
            updatable = true)
    private boolean mustChangePassword;



    @OneToOne(mappedBy = "administrator",
              fetch = FetchType.LAZY, 
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, 
              orphanRemoval = true,
              optional = false)
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY,
               cascade = {})
    @JoinColumn(name = "manager_id", 
                nullable = true,
                updatable = true,
                foreignKey = @ForeignKey(name = "admin_to_admin_fk"))
    private Administrator manager;

    @OneToMany(mappedBy = "manager", 
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<Administrator> collaborators = new ArrayList<>();

    @OneToMany(mappedBy = "administrator", 
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<Agent> agents = new ArrayList<>();



    public Administrator(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    }



    public void addAgency(Agency newAgency) 
    {
        this.setAgency(newAgency);
        newAgency.setAdministrator(this);

        
    }    
    
    public void removeAgency() 
    {
        this.getAgency().setAdministrator(null);
        this.setAgency(null);

        
    }


    public void setAgency(Agency newAgency) 
    {
        if (newAgency == null) 
            this.agency.setAdministrator(null);
        else 
            newAgency.setAdministrator(this);

        this.agency = newAgency;
    }


    public void addCollaborator(Administrator newCollaborator) 
    {
       this.collaborators.add(newCollaborator);
       newCollaborator.setManager(this);
    }  
    
    public void removeCollaborator(Administrator collaboratorToRemove)
    {
        this.collaborators.remove(collaboratorToRemove);
        collaboratorToRemove.setManager(null);
    }

    public void addAgent(Agent newAgent) 
    {
       this.agents.add(newAgent);
       newAgent.setAdministrator(this);
    }  
    
    public void removeAgent(Agent agentToRemove)
    {
        this.agents.remove(agentToRemove);
        agentToRemove.setAdministrator(null);
    }
}