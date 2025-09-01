
package com.dietiestates.backend.dto.request.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import com.dietiestates.backend.enums.EnergyClass;
import com.dietiestates.backend.enums.FurnitureCondition;
import com.dietiestates.backend.enums.PropertyCondition;
import com.dietiestates.backend.validator.groups.OnCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le caratteristiche principali dell'immobile, come prezzo, dimensione, numero di stanze e classe energetica.")
public class RealEstateMainFeaturesDto 
{
    @NotBlank(groups = OnCreate.class)
    @Size(max = 35)
    @Schema(description = "Titolo sintetico dell'annuncio dell'immobile.", example = "Appartamento luminoso in centro città")
    private String title;

    @NotBlank(groups = OnCreate.class)
    @Size(min = 250)
    @Schema(description = "Descrizione dettagliata dell'immobile.", 
            example = "Ampio appartamento di 100 m² situato in centro città, composto da tre camere da letto luminose, cucina abitabile completamente attrezzata, servizi moderni, " + 
                      "soggiorno con balcone panoramico, pavimenti in parquet, riscaldamento autonomo, aria condizionata e garage privato. L'immobile è vicino a scuole, parchi, " + 
                      "supermercati e mezzi di trasporto pubblico, rendendolo ideale per famiglie e professionisti.")
    private String description;
    
    @NotNull(groups = OnCreate.class)
    @Positive  
    @Schema(description = "Prezzo dell'immobile, in euro.", example = "250000")
    private Double price;
    
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero    
    @Schema(description = "Spese condominiali mensili, in euro.", example = "150")
    private Double condoFee;
    
    @NotNull(groups = OnCreate.class)
    @Positive       
    @Schema(description = "Dimensione dell'immobile in metri quadrati.", example = "100")
    private Double size;

    @NotNull(groups = OnCreate.class)
    @Positive   
    @Schema(description = "Numero di stanze dell'immobile.", example = "3")
    private Integer roomsNumber;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero    
    @Schema(description = "Piano in cui si trova l'immobile.", example = "2")
    private Integer floorNumber;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero  
    @Schema(description = "Numero di posti auto disponibili.", example = "1")  
    private Integer parkingSpacesNumber;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Numero di posti auto disponibili.", example = "1")
    private EnergyClass energyClass;
    
    @NotNull(groups = OnCreate.class)
    @Schema(description = "Condizione generale dell'immobile.", example = "Habitable")
    private PropertyCondition propertyCondition;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Condizione degli arredi presenti nell'immobile.", example = "Well fournished")
    private FurnitureCondition furnitureCondition;
}