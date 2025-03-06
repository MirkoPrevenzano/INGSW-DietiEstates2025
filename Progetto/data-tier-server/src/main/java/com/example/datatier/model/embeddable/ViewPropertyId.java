package com.example.datatier.model.embeddable;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ViewPropertyId implements Serializable { 
    //serve quando si ha associazione molti a molti si dichiara una classe contenente primary key composte

    private Long idCustomer;
    private Long idProperty;

    public ViewPropertyId() {
    }


    public Long getIdCustomer() {
        return idCustomer;
    }
    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdProperty() {
        return idProperty;
    }
    public void setIdProperty(Long idProperty) {
        this.idProperty = idProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewPropertyId that = (ViewPropertyId) o;
        return Objects.equals(idCustomer, that.idCustomer) &&
               Objects.equals(idProperty, that.idProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, idProperty);
    }
}