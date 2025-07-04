
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;



@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CustomerViewsRealEstateId implements Serializable 
{ 
    private Long customerId;
    private Long realEstateId;



    @Override
    public boolean equals(Object o) 
    {
        if(this == o) 
            return true;

        if(o == null || this.getClass() != o.getClass()) 
            return false;

        CustomerViewsRealEstateId that = (CustomerViewsRealEstateId) o;
        return Objects.equals(customerId, that.customerId) &&
               Objects.equals(realEstateId, that.realEstateId);
    }

    
    @Override
    public int hashCode() 
    {
        return Objects.hash(customerId, realEstateId);
    }
}