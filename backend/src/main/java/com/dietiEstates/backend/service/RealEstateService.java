
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiEstates.backend.dto.response.RealEstateSearchDto;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDto;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiEstates.backend.extra.CoordinatesBoundingBox;
import com.dietiEstates.backend.mapper.RealEstateCreationDtoMapper;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.resolver.RealEstateMapperResolver;
import com.dietiEstates.backend.util.FindByRadiusUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final RealEstateMapperResolver realEstateMapperResolver;

    
    public RealEstateSearchDto search(Map<String,String> filters, Pageable page)
    {
        CoordinatesBoundingBox coordinatesBoundingBox = FindByRadiusUtil.getBoundingBox(Integer.valueOf(filters.get("radius")), 
                                                                                        Double.valueOf(filters.get("lat")), 
                                                                                        Double.valueOf(filters.get("lon")));

        Page<RealEstatePreviewInfoDto> realEstatePreviewsPage = realEstateRepository.findRealEstatePreviewInfosByFilters(filters, page, coordinatesBoundingBox);

        RealEstateSearchDto realEstateSearchDto = new RealEstateSearchDto(realEstatePreviewsPage.getContent(),
                                                                          realEstatePreviewsPage.getNumberOfElements(),
                                                                          realEstatePreviewsPage.getTotalElements(), 
                                                                          realEstatePreviewsPage.getTotalPages());
        return realEstateSearchDto;
    }


    @Transactional
    public RealEstateCompleteInfoDto getRealEstateCompleteInfo(Long realEstateId, Authentication authentication)
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));
        
        Agent agent = realEstate.getAgent();
        AgentPublicInfoDto agentPublicInfoDto = modelMapper.map(agent, AgentPublicInfoDto.class);    

        RealEstateCreationDtoMapper realEstateCreationDtoMapper = realEstateMapperResolver.getMapper(realEstate);
        RealEstateCreationDto realEstateCreationDto = realEstateCreationDtoMapper.toDto(realEstate);

        RealEstateCompleteInfoDto realEstateCompleteInfoDto = new RealEstateCompleteInfoDto(realEstateCreationDto, agentPublicInfoDto);

        if(authentication != null && authentication.isAuthenticated() && authentication.getAuthorities()
                                                                                       .stream()
                                                                                       .anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER"))) 
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
            customerRepository.saveAndFlush(customer);
        }

        return realEstateCompleteInfoDto;
    }
}