
package com.dietiestates.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AdditionalPropertiesValue;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiestates.backend.dto.response.RealEstateSearchDto;
import com.dietiestates.backend.service.RealEstateService;
import com.dietiestates.backend.service.photo.PhotoResult;
import com.dietiestates.backend.validator.RealEstateFiltersValidator;
import com.dietiestates.backend.validator.groups.OnCreate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/real-estates")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RealEstateController 
{
    private final RealEstateService realEstateService;


    @PostMapping
    @Operation(description = "Creazione di un nuovo annuncio immobiliare.",
               tags = "Real Estates")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Annuncio immobiliare creato con successo!",
                                content = @Content(mediaType = "application/json", 
                                                   schema = @Schema(description = "ID dell'annuncio immobiliare appena creato.",
                                                                    type = "integer",
                                                                    format = "int64",
                                                                    example = "3")))})
    public ResponseEntity<Long> createRealEstate(@Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateCreationDto realEstateCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(realEstateService.createRealEstate(userDetails.getUsername(), realEstateCreationDto));
                                 
    }

    @PostMapping(value = "/{realEstateId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Inserimento di foto relative ad un annuncio immobiliare.",
               tags = "Real Estates")
    @Parameter(description = "Lista di foto da aggiungere.",
               name = "photos", 
               content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(type = "array", implementation = MultipartFile.class)),
               example = "photo1.png, photo2.jpg")
    @Parameter(description = "ID dell'annuncio immobiliare",
               name = "realEstateId", 
               example = "2")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "Foto dell'annuncio immobiliare inserite con successo!")})
    public ResponseEntity<Void> uploadPhotos(Authentication authentication,
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId) throws IOException
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        realEstateService.uploadPhotos(userDetails.getUsername(), file, realEstateId);
        return ResponseEntity.ok().build();            
    }

    @GetMapping(value = "/{realEstateId}/photos")
    @Operation(description = "Recupero di tutte le foto relative ad un annuncio immobiliare.",
               tags = "Real Estates")
    @Parameter(description = "ID dell'annuncio immobiliare",
               name = "realEstateId", 
               example = "9")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "Foto dell'annuncio immobiliare recuperate con successo!")})
    public ResponseEntity<List<PhotoResult<String>>> getPhotos(@PathVariable("realEstateId") Long realEstateId)
    {        
        return ResponseEntity.ok(realEstateService.getPhotos(realEstateId));
    }
   
    @GetMapping
    @Operation(description = "Recupero di tutti gli annunci immobiliari che rispettano determinati filtri.\n\n" + 
                             "I filtri opzionali da poter aggiungere alla query sono: 'minPrice', 'maxPrice', 'rooms', " + 
                             "'airConditioning', 'heating', 'elevator', 'concierge', 'terrace', 'garage', 'balcony', " + 
                             "'garden', 'swimmingPool', 'isNearSchool', 'isNearPark', 'isNearPublicTransport'.",
               tags = "Real Estates")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "Annunci immobiliari recuperati con successo!")})
    @Parameter(description = "Numero di pagina da recuperare",
               name = "page", 
               example = "1")
    @Parameter(description = "Valore massimo di annunci immobiliare da recuperare",
               name = "limit", 
               example = "50")
    @Parameter(description = "Lista di filtri opzionali da applicare.",
               name = "filters",
               required = false, 
               example = "?minPrice=100000&maxPrice=500000&rooms=3&airConditioning=true&heating=true" +
                      "&elevator=false&concierge=true&terrace=true&garage=false&balcony=true" +
                      "&garden=false&swimmingPool=true&isNearSchool=true&isNearPark=false" +
                      "&isNearPublicTransport=true")
    public ResponseEntity<RealEstateSearchDto> search(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, 
                                                   @Valid @RealEstateFiltersValidator @RequestParam(name = "filters", required = false) Map<String,String> filters) 
    {
        RealEstateSearchDto realEstateSearchDto = realEstateService.search(filters, PageRequest.of(page, limit));                
        return ResponseEntity.ok(realEstateSearchDto);
    }

    @GetMapping("/{realEstateId}")
    @Operation(description = "Recupero di tutte le informazioni dettagliate relative ad un annuncio immobiliare.",
               tags = "Real Estates")
    @Parameter(description = "ID dell'annuncio immobiliare",
               name = "realEstateId", 
               example = "5")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "Info dell'annuncio immobiliare recuperate con successo!")})
    public ResponseEntity<RealEstateCompleteInfoDto> getRealEstateCompleteInfo(@PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok(realEstateService.getRealEstateCompleteInfo(realEstateId, authentication));
    }
}