
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.response.AgentPublicInfoDTO;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDTO;
import com.dietiEstates.backend.dto.response.RealEstateSearchDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.util.FindByRadiusUtil;
import com.dietiEstates.backend.util.RealEstateMappingUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;
    private final FindByRadiusUtil findByRadiusUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RealEstateMappingUtil realEstateMappingUtil;


    public RealEstateSearchDTO search3(Map<String,String> filters, Pageable page)
    {
        CoordinatesMinMax coordinatesMinMax = findByRadiusUtil.calcoloLatLongMinMax(Integer.valueOf(filters.get("radius")), 
                                                                               Double.valueOf(filters.get("lat")), 
                                                                               Double.valueOf(filters.get("lon")));

        Page<RealEstatePreviewInfoDTO> realEstatePreviewsPage = realEstateRepository.findPreviewsByFilters(filters, page, coordinatesMinMax);

        RealEstateSearchDTO RealEstatePreviewsFirstPageDTO = new RealEstateSearchDTO(realEstatePreviewsPage.getContent(),
                                                                                                        realEstatePreviewsPage.getTotalElements(), 
                                                                                                        realEstatePreviewsPage.getTotalPages());
        return RealEstatePreviewsFirstPageDTO;
    }


    public String getRealEstateCompleteInfo(Long realEstateId, Authentication authentication)
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));
        
        Agent agent = realEstate.getAgent();
        AgentPublicInfoDTO agentPublicInfoDTO = modelMapper.map(agent, AgentPublicInfoDTO.class);
        RealEstateCreationDTO realEstateCreationDTO = realEstateMappingUtil.realEstateCreationDTOMapper(realEstate);

        RealEstateCompleteInfoDTO realEstateCompleteInfoDTO = new RealEstateCompleteInfoDTO(realEstateCreationDTO, agentPublicInfoDTO);

        // 2. Verifica se l'utente autenticato ha il ruolo CUSTOMER
        if(authentication != null && authentication.isAuthenticated() && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) 
        {
            if(authentication.getPrincipal() instanceof Customer)
                log.info("\n\nil mio principal è un customerrrr\n\n");

            UserDetails user = (UserDetails) authentication.getPrincipal();

            log.info("\n\n\nSONO NELL'IF DEL CUSTOMERR");
        }

        log.info("\n\n\nSONO FUORI IF...");

        return "ciao";
    }
}