
package com.dietiestates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.factory.RealEstateFromDtoFactory;

import lombok.RequiredArgsConstructor;

/*
 * il pattern resolver, in generale, viene utilizzato per risolvere e restituire l'implementazione appropriata. 
 * Questo pattern è particolarmente utile quando si lavora con più implementazioni di un'interfaccia o di una classe astratta
 * e si desidera selezionare dinamicamente l'implementazione corretta in base a determinate condizioni o criteri.
 * Il class diagram di un resolver generalmente include i seguenti componenti:
 * 1. Interfaccia o Classe Astratta: Definisce il contratto che tutte le implementazioni devono seguire.
 * 2. Implementazioni Concrette: Diverse classi che implementano l'interfaccia o estendono la classe astratta.
 * 3. Resolver: Una classe che contiene la logica per selezionare e restituire l'implementazione appropriata in base ai criteri specificati.
 * In questo caso specifico, il resolver RealEstateFromDtoFactoryResolver seleziona la factory corretta per creare un'entità RealEstate
 * in base al tipo di RealEstateCreationDto fornito (ad esempio, RealEstateForSaleCreationDto o RealEstateForRentCreationDto).
 */
@Component
@RequiredArgsConstructor
public class RealEstateFromDtoFactoryResolver 
{
    private final List<RealEstateFromDtoFactory> realEstateFromDtoFactories;


    
    public RealEstateFromDtoFactory getFactory(RealEstateCreationDto dto) 
    {
        return realEstateFromDtoFactories.stream()
                                         .filter(factory -> factory.supports(dto))
                                         .findFirst()
                                         .orElseThrow(() -> new IllegalArgumentException("Nessuna RealEstateFromDtoFactory supporta il tipo: " + dto.getClass().getSimpleName()));
    }
}