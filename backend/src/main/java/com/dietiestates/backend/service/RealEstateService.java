
package com.dietiestates.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.response.AgentPublicInfoDto;
import com.dietiestates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiestates.backend.dto.response.RealEstateSearchDto;
import com.dietiestates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiestates.backend.extra.CoordinatesBoundingBox;
import com.dietiestates.backend.factory.RealEstateFromDtoFactory;
import com.dietiestates.backend.mapper.RealEstateCreationDtoMapper;
import com.dietiestates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.model.entity.Customer;
import com.dietiestates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiestates.backend.model.entity.Photo;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.repository.AgentRepository;
import com.dietiestates.backend.repository.CustomerRepository;
import com.dietiestates.backend.repository.RealEstateRepository;
import com.dietiestates.backend.resolver.RealEstateFromDtoFactoryResolver;
import com.dietiestates.backend.resolver.RealEstateMapperResolver;
import com.dietiestates.backend.service.mock.MockingStatsService;
import com.dietiestates.backend.service.photo.PhotoResult;
import com.dietiestates.backend.service.photo.PhotoService;
import com.dietiestates.backend.strategy.AgentLoadingStrategy;
import com.dietiestates.backend.strategy.CustomerLoadingStrategy;
import com.dietiestates.backend.util.FindByRadiusUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final AgentRepository agentRepository;

    private final AgentLoadingStrategy agentLoadingStrategy;

    private final RealEstateFromDtoFactoryResolver realEstateFromDtoFactoryResolver;

    private final MockingStatsService mockingStatsService;

    private final PhotoService photoService;

    private final RealEstateRepository realEstateRepository;

    private final CustomerRepository customerRepository;

    private final CustomerLoadingStrategy customerLoadingStrategy;

    private final RealEstateMapperResolver realEstateMapperResolver;



    @Transactional
    public Long createRealEstate(String username, RealEstateCreationDto realEstateCreationDto) throws UsernameNotFoundException
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);
        
        RealEstateFromDtoFactory realEstateFromDtoFactory = realEstateFromDtoFactoryResolver.getFactory(realEstateCreationDto);
        RealEstate realEstate = realEstateFromDtoFactory.create(realEstateCreationDto);

        mockingStatsService.mockRealEstateStats(realEstate);

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
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));
                                                            
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


    public List<PhotoResult<String>> getPhotos(Long realEstateId)
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));

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

        return new RealEstateSearchDto(realEstatePreviewsPage.getContent(),
                                       realEstatePreviewsPage.getNumberOfElements(),
                                       realEstatePreviewsPage.getTotalElements(), 
                                       realEstatePreviewsPage.getTotalPages());
    }


    public RealEstateCompleteInfoDto getRealEstateCompleteInfo(Long realEstateId, Authentication authentication)
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + realEstateId));

        Long agentId = realEstate.getAgent().getUserId();
        AgentPublicInfoDto agentPublicInfoDto = agentRepository.findAgentPublicInfoById(agentId);

        RealEstateCreationDtoMapper realEstateCreationDtoMapper = realEstateMapperResolver.getMapper(realEstate);
        RealEstateCreationDto realEstateCreationDto = realEstateCreationDtoMapper.toDto(realEstate);

        RealEstateCompleteInfoDto realEstateCompleteInfoDto = new RealEstateCompleteInfoDto(realEstateCreationDto, agentPublicInfoDto);

        if(authentication != null && authentication.isAuthenticated() && authentication.getAuthorities()
                                                                                       .stream()
                                                                                       .anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER"))) 
        {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            /*Customer customer = customerRepository.findByUsername(userDetails.getUsername())
                                                  .orElseThrow(() -> new IllegalArgumentException("Customer non trovato: " + userDetails.getUsername()));*/

            Customer customer = (Customer) customerLoadingStrategy.loadUser(userDetails.getUsername());

            realEstate.getRealEstateStats().incrementViewsNumber();

            CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(new CustomerViewsRealEstateId(customer.getUserId(), realEstate.getRealEstateId()), 
                                                                                          LocalDateTime.now(), 
                                                                                          customer,
                                                                                          realEstate);

            customer.addCustomerViewsRealEstate(customerViewsRealEstate);

            customerRepository.save(customer);
        }

        return realEstateCompleteInfoDto;
    }
}