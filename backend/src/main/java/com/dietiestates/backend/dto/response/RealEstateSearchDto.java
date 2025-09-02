
package com.dietiestates.backend.dto.response;

import java.util.List;

import com.dietiestates.backend.dto.response.support.RealEstatePreviewInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta il risultato di una ricerca immobiliare, comprensivo degli immobili trovati e delle informazioni di paginazione.")
public class RealEstateSearchDto
{
    @Schema(description = "Lista delle anteprime degli annunci immobiliari trovati nella ricerca.")
    private List<RealEstatePreviewInfoDto> realEstatePreviewInfoDtoList;

    @Schema(description = "Numero totale di elementi presenti nella pagina corrente.", example = "10")
    private int totalElementsOfPage;

    @Schema(description = "Numero complessivo di elementi trovati nella ricerca.", example = "125")
    private long totalElements;
    
    @Schema(description = "Numero totale di pagine, calcolato in base ai risultati e alla dimensione della pagina.", example = "13")
    private int totalPages;
}