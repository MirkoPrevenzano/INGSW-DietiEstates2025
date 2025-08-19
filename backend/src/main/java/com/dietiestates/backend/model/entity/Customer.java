
package com.dietiestates.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Customer")
@Table(name = "customer", 
       uniqueConstraints = @UniqueConstraint(name = "customer_uk", columnNames = "username"))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true, 
          exclude = "customerViewsRealEstates")
public class Customer extends User
{
    @Column(name = "is_auth_with_external_api", nullable = false)
    private boolean isAuthWithExternalAPI;//valutare se creare una classe d'appoggio


    @OneToMany(mappedBy = "customer", 
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<CustomerViewsRealEstate> customerViewsRealEstates = new ArrayList<>();


    public Customer(String name, String surname, String username, String password) 
    {
        super(name, surname, username, password);
    }


    public void addCustomerViewsRealEstate(CustomerViewsRealEstate newCustomerViewsRealEstate) 
    {
        this.customerViewsRealEstates.add(newCustomerViewsRealEstate);
        newCustomerViewsRealEstate.getRealEstate()
                                  .getCustomerViewsRealEstates()
                                  .add(newCustomerViewsRealEstate);
    }
    
    public void removeCustomerViewsRealEstate(CustomerViewsRealEstate customerViewsRealEstateToRemove) 
    {
        this.customerViewsRealEstates.remove(customerViewsRealEstateToRemove);
        customerViewsRealEstateToRemove.getRealEstate()
                                       .getCustomerViewsRealEstates()
                                       .remove(customerViewsRealEstateToRemove);
    }
}