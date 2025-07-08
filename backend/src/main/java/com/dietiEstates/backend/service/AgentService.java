
package com.dietiEstates.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.config.ModelMapperConfig;
import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.response.AgentStatsDTO;
import com.dietiEstates.backend.dto.response.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.response.RealEstateStatsDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.factory.RealEstateFactory;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.factory.resolver.RealEstateFactoryResolver;
import com.dietiEstates.backend.helper.MockingStatsHelper;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.AmazonS3Util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentService 
{
    private final AgentRepository agentRepository;
    private final RealEstateRepository realEstateRepository;
    private final AmazonS3Util amazonS3Util;
    private final MockingStatsHelper mockingStatsHelper;
    private final ModelMapper modelMapper;
    //private final ValidationUtil validationUtil;
    private final RealEstateFactory realEstateFactory;
    private final RealEstateFactoryResolver realEstateFactoryResolver;



    @Transactional
    public Long createRealEstate(String username, RealEstateCreationDTO realEstateCreationDTO)  throws UsernameNotFoundException
    {
        Optional<Agent> optionalRealEstateAgent = agentRepository.findByUsername(username);
        Agent agent = optionalRealEstateAgent.get();
        
        RealEstateFromDtoFactory realEstateFromDtoFactory = realEstateFactoryResolver.getFactory(realEstateCreationDTO);
        RealEstate realEstate = realEstateFromDtoFactory.create(realEstateCreationDTO);

        Address address = modelMapper.map(realEstateCreationDTO.getAddressDTO(), Address.class);
        realEstate.addAddress(address);

        mockingStatsHelper.mockEstateStats(realEstate);

        agent.addRealEstate(realEstate);

        int newTotalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates() + 1;
        agent.getAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        agent.addRealEstate(realEstate);

        agent = agentRepository.save(agent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastUploadedByAgent(agent.getUserId());
    }



    public void uploadPhoto(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException
    {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate realEstate = optionalRealEstate.get();
        
/*         if(files.length < 3 || files.length > 10)
        {
            log.error("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
            throw new IllegalArgumentException("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
        } */

        for(MultipartFile multipartFile : files)
        {
            String contentType = multipartFile.getContentType();
            Long size = multipartFile.getSize();
    
            log.info("file content type: {}", contentType);
            log.info("file size: {}", size);
    
            if(contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png"))
            {
                log.error("Photo format is not supported!");
                throw new IllegalArgumentException("Photo format is not supported!");
            }
    
            if(size >= 10000000)
            {
                log.error("Photo have exceeded the maximum size!");
                throw new IllegalArgumentException("Photo have exceeded the maximum size!");
            }
    
            String photoKey = UUID.randomUUID().toString();
            try 
            {
                amazonS3Util.putObject("%s".formatted(photoKey), multipartFile.getBytes());
            } 
            catch (SdkException | IOException e) 
            {
                log.error("Amazon S3/SDK/IO exception has occurred while putting photo in the bucket!" + e.getMessage());
                throw new RuntimeException("Failed to upload photo!", e);
            }
    
            Photo photo = new Photo(photoKey);
            realEstate.getPhotos().add(photo);            
        }

        realEstateRepository.save(realEstate);
    }

    
    public String[] getPhoto(Long realEstateId) throws IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();

        List<Photo> photos = realEstate.getPhotos();
        if (photos == null || photos.isEmpty()) {
            return new String[]{};
        }
        
        List<byte[]> photosBytes = new ArrayList<>();

        for(Photo photo : photos)
        {
            photosBytes.add(amazonS3Util.getObject(photo.getAmazonS3Key()));
        }

        String[] phoStrings = new String[photosBytes.size()];
        for (int i=0;i<photosBytes.size();i++) {
            phoStrings[i]=Base64.getEncoder().encodeToString(photosBytes.get(i));
        }

        return phoStrings;
    }
   


    public List<RealEstateRecentDTO> findRecentRealEstates(String username, Integer limit) 
    {
        return realEstateRepository.findRecentsByAgent(agentRepository.findByUsername(username).get().getUserId(), limit);
    }


    public AgentStatsDTO getAgentStats(String username) 
    {
        Integer[] estatesPerMonth = mockingStatsHelper.mockBarChartStats();
        AgentStats agentStats = agentRepository.findByUsername(username).get().getAgentStats();
        AgentStatsDTO agentStatsDTO = new AgentStatsDTO(agentStats, estatesPerMonth);
        return agentStatsDTO;
    }

    
    public List<RealEstateStatsDTO> getRealEstateStats(String username, Pageable page) 
    {
        return realEstateRepository.findStatsByAgent(agentRepository.findByUsername(username).get().getUserId(), 
                                                        page);
    }

    public Integer[] getBarChartStats() 
    {
        return mockingStatsHelper.mockBarChartStats();
    }
}