
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.response.AgentPublicInfoDTO;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDTO;
import com.dietiEstates.backend.dto.response.RealEstateSearchDTO;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.RealEstate;
//import com.dietiEstates.backend.repository.CVRRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.FindByRadiusUtil;
import com.dietiEstates.backend.util.RealEstateMappingUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;
    private final FindByRadiusUtil findByRadiusUtil;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final RealEstateMappingUtil realEstateMappingUtil;
    //private final CVRRepository cvrRepository;


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


    @Transactional
    public RealEstateCompleteInfoDTO getRealEstateCompleteInfo(Long realEstateId, Authentication authentication)
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));
        
        Agent agent = realEstate.getAgent();
        AgentPublicInfoDTO agentPublicInfoDTO = modelMapper.map(agent, AgentPublicInfoDTO.class);        
        RealEstateCreationDTO realEstateCreationDTO = realEstateMappingUtil.realEstateCreationDTOMapper(realEstate);

        RealEstateCompleteInfoDTO realEstateCompleteInfoDTO = new RealEstateCompleteInfoDTO(realEstateCreationDTO, agentPublicInfoDTO);

        if(authentication != null && authentication.isAuthenticated() && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) 
        {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Customer customer = customerRepository.findByUsername(userDetails.getUsername())
                                                  .orElseThrow(() -> new IllegalArgumentException("Customer non trovato: " + userDetails.getUsername()));

            long newViewsNumber = realEstate.getRealEstateStats().getViewsNumber() + 1;
            realEstate.getRealEstateStats().setViewsNumber(newViewsNumber);
        
            CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(new CustomerViewsRealEstateId(customer.getUserId(), realEstate.getRealEstateId()), 
                                                                                          LocalDateTime.now(), 
                                                                                          customer,
                                                                                          realEstate);

            customer.addCustomerViewsRealEstate(customerViewsRealEstate);
            //cvrRepository.save(customerViewsRealEstate);
            //customerRepository.save(customer);
            //realEstateRepository.save(realEstate);
        }

        return realEstateCompleteInfoDTO;
    }
}