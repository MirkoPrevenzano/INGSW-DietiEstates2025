
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.response.RealEstateSearchDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.FindByRadiusUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;
    private final FindByRadiusUtil findByRadiusUtil;



    public RealEstateSearchDTO search3(Map<String,String> filters, Pageable page)
    {
        CoordinatesMinMax coordinatesMinMax = findByRadiusUtil.calcoloLatLongMinMax(Integer.valueOf(filters.get("radius")), 
                                                                               Double.valueOf(filters.get("lat")), 
                                                                               Double.valueOf(filters.get("lon")));

        Page<RealEstatePreviewDTO> realEstatePreviewsPage = realEstateRepository.findPreviewsByFilters(filters, page, coordinatesMinMax);

        RealEstateSearchDTO RealEstatePreviewsFirstPageDTO = new RealEstateSearchDTO(realEstatePreviewsPage.getContent(),
                                                                                                        realEstatePreviewsPage.getTotalElements(), 
                                                                                                        realEstatePreviewsPage.getTotalPages());
        return RealEstatePreviewsFirstPageDTO;
    }


    public String getRealEstateDetails(Authentication authentication)
    {
        // 2. Verifica se l'utente autenticato ha il ruolo CUSTOMER
        if (authentication != null && authentication.isAuthenticated() &&
            authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) 
        {
            log.info("\n\n\nSONO NELL'IF DEL ADMINNNN");
        }

        if (authentication != null && authentication.isAuthenticated() &&
        authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"))) 
    {
        log.info("\n\n\nSONO NELL'IF DEL AGENTTT");
    }
        log.info("\n\n\nSONO FUORI IF...");

        return "ciao";
    }
}