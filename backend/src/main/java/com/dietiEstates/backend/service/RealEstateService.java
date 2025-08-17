
package com.dietiEstates.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiEstates.backend.dto.response.RealEstateSearchDto;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDto;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiEstates.backend.extra.CoordinatesBoundingBox;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.mapper.RealEstateCreationDtoMapper;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.resolver.RealEstateFromDtoFactoryResolver;
import com.dietiEstates.backend.resolver.RealEstateMapperResolver;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.service.photo.PhotoResult;
import com.dietiEstates.backend.service.photo.PhotoService;
import com.dietiEstates.backend.util.FindByRadiusUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final AgentRepository agentRepository;
    private final RealEstateFromDtoFactoryResolver realEstateFromDtoFactoryResolver;
    private final MockingStatsService mockingStatsService;
    private final PhotoService photoService;
    private final RealEstateRepository realEstateRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final RealEstateMapperResolver realEstateMapperResolver;


    @Transactional
    public Long createRealEstate(String username, RealEstateCreationDto realEstateCreationDto) throws UsernameNotFoundException
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
        
        RealEstateFromDtoFactory realEstateFromDtoFactory = realEstateFromDtoFactoryResolver.getFactory(realEstateCreationDto);
        RealEstate realEstate = realEstateFromDtoFactory.create(realEstateCreationDto);

        // Address address = modelMapper.map(realEstateCreationDto.getAddressDto(), Address.class);
        // realEstate.addAddress(address);

        mockingStatsService.mockRealEstateStats(realEstate);

        //agent.addRealEstate(realEstate);

/*         int newTotalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates() + 1;
        agent.getAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates); */

        agent.getAgentStats().incrementTotalUploadedRealEstates();
        agent.addRealEstate(realEstate);

        agent = agentRepository.save(agent);

        log.info("Real Estate was created successfully!");

        return realEstateRepository.findLastUploadedByAgentId(agent.getUserId());
    }



    @Transactional
    public void uploadPhotos(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException, IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new UsernameNotFoundException(""));
                                                            
        if(files.length < 3 || files.length > 10)
        {
            log.error("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
            throw new IllegalArgumentException("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
        }

        for(MultipartFile file : files)
        {
            String photoKey = photoService.uploadPhoto(file, "real-estates/" + realEstateId);
    
            Photo photo = new Photo(photoKey);
            realEstate.getPhotos().add(photo);            
        }

        realEstateRepository.save(realEstate);
    }


    public List<PhotoResult<String>> getPhotos(Long realEstateId) throws IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new UsernameNotFoundException(""));

        List<Photo> photos = realEstate.getPhotos();

        List<PhotoResult<String>> photoResults = new ArrayList<>();
        for(Photo photo : photos)
            photoResults.add(photoService.getPhotoAsBase64(photo.getKey()));


        return photoResults;
    }

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

/*             long newViewsNumber = realEstate.getRealEstateStats().getViewsNumber() + 1;
            realEstate.getRealEstateStats().setViewsNumber(newViewsNumber); */

            realEstate.getRealEstateStats().incrementViewsNumber();

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








/* package com.dietiEstates.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiEstates.backend.dto.response.RealEstateSearchDto;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDto;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiEstates.backend.extra.CoordinatesBoundingBox;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.mapper.RealEstateCreationDtoMapper;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.resolver.RealEstateMapperResolver;
import com.dietiEstates.backend.service.photo.PhotoResult;
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
} */