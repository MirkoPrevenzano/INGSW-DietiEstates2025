
package com.dietiEstates.backend.factory;
 
import com.dietiEstates.backend.dto.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public final class RealEstateRootFactory 
{
    private RealEstateRootFactory() {};
    

    public static Root<?> createFromType(String realEstateType, CriteriaQuery<?> criteriaQuery)
    {
            if(realEstateType.equals("For Sale"))
                return criteriaQuery.from(RealEstateForRent.class);
            else if(realEstateType.equals("For Rent"))
                return criteriaQuery.from(RealEstateForRent.class);
            else
                return null; // TODO: lanciare eccezione.
    }
}


// TODO: provare ad implementare la query polimorfica con treat() o type()
/* public class RealEstateTypeFilterFactory {

    // Questo metodo crea il Predicate corretto per filtrare per tipo di immobile
    public static Predicate createTypePredicate(CriteriaBuilder cb, Root<RealEstate> realEstateRoot, String typeFilter) {
        if (typeFilter == null || typeFilter.isEmpty() || "ALL".equalsIgnoreCase(typeFilter)) {
            // Se non c'è un filtro di tipo, restituisci un predicato "vero" che non filtra nulla
            return cb.isTrue(cb.literal(true));
        } else if ("FOR_SALE".equalsIgnoreCase(typeFilter)) {
            return cb.isInstanceOf(realEstateRoot, RealEstateForSale.class);
        } else if ("FOR_RENT".equalsIgnoreCase(typeFilter)) {
            return cb.isInstanceOf(realEstateRoot, RealEstateForRent.class);
        } else {
            // Gestisci tipi sconosciuti: restituisci un predicato "falso" per non trovare risultati
            // o potresti lanciare un'eccezione se un tipo non valido è un errore.
            return cb.isFalse(cb.literal(true));
        }
    }
} */