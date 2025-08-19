
package com.dietiestates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.factory.RealEstateRootFactory;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootFactoryResolver 
{
    private final List<RealEstateRootFactory<? extends RealEstate>> realEstateRootFactories;


    @SuppressWarnings("unchecked")
    public <R extends RealEstate> RealEstateRootFactory<R> getFactory(ContractType contractType) 
    {
        return (RealEstateRootFactory<R>) realEstateRootFactories.stream()
                                                                 .filter(factory -> factory.supports(contractType))
                                                                 .findFirst()
                                                                 .orElseThrow(() -> new IllegalArgumentException("Nessuna RealEstateRootFactory supporta il tipo: " + contractType));
    }
}

