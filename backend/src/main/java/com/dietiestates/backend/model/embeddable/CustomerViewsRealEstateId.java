
package com.dietiestates.backend.model.embeddable;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Embeddable
@Getter
@ToString
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