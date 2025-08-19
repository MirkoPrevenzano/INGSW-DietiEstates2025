
package com.dietiestates.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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



@Entity(name = "Administrator")
@Table(name = "administrator",
       uniqueConstraints = @UniqueConstraint(name = "administrator_uk", columnNames = "username"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true, 
          exclude = {"collaborators", "agents"})
public class Administrator extends User
{
    @ManyToOne(fetch = FetchType.LAZY,
               cascade = {})
    @JoinColumn(name = "agency_id", 
                nullable = true,
                updatable = true,
                foreignKey = @ForeignKey(name = "admin_to_agency_fk"))
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY,
               cascade = {})
    @JoinColumn(name = "manager_id", 
                nullable = true,
                updatable = true,
                foreignKey = @ForeignKey(name = "collaborator_to_admin_fk"))
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