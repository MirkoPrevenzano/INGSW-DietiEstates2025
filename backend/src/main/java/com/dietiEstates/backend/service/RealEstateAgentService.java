
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
import com.dietiEstates.backend.dto.RealEstateAgentStatsDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.EstateCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.factory.RealEstateFactory;
import com.dietiEstates.backend.model.User;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.RealEstateAgentStats;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateAgent;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.utils.MockingStatsUtil;
import com.dietiEstates.backend.utils.S3Util;
import com.dietiEstates.backend.utils.ValidationUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateAgentService 
{
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final RealEstateRepository realEstateRepository;
    private final S3Util s3Util;
    private final MockingStatsUtil mockingStatsUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final RealEstateFactory realEstateFactory;



    @Transactional
    public Long createRealEstateForSale(String username, RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)  throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> optionalRealEstateAgent = realEstateAgentRepository.findByUsername(username);
        RealEstateAgent realEstateAgent = validationUtil.optionalUserValidator(optionalRealEstateAgent, username);
        
        RealEstate realEstate = realEstateFactory.createRealEstateFromDTO(realEstateForSaleCreationDTO);


        Address address = modelMapper.map(realEstateForSaleCreationDTO.getAddressDTO(), Address.class);
        realEstate.addAddress(address);

        mockingStatsUtil.mockEstateStats(realEstate);

        realEstateAgent.addRealEstate(realEstate);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        realEstateAgent.addRealEstate(realEstate);

        realEstateAgent = realEstateAgentRepository.save(realEstateAgent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastRealEstate(realEstateAgent.getUserId());
    }


    @Transactional
    public Long createRealEstateForRent(String username, RealEstateForRentCreationDTO realEstateForRentCreationDTO) throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> optionalRealEstateAgent = realEstateAgentRepository.findByUsername(username);
        RealEstateAgent realEstateAgent = validationUtil.optionalUserValidator(optionalRealEstateAgent, username);

        RealEstate realEstate = realEstateFactory.createRealEstateFromDTO(realEstateForRentCreationDTO);


        Address address = modelMapper.map(realEstateForRentCreationDTO.getAddressDTO(), Address.class);
        realEstate.addAddress(address);

        mockingStatsUtil.mockEstateStats(realEstate);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        realEstateAgent.addRealEstate(realEstate);
        
        realEstateAgent = realEstateAgentRepository.save(realEstateAgent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastRealEstate(realEstateAgent.getUserId());
    }


    public void uploadPhoto(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException
    {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate realEstate = validationUtil.optionalRealEstateValidator(optionalRealEstate, realEstateId);
        
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
                s3Util.putObject("%s".formatted(photoKey), multipartFile.getBytes());
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
            photosBytes.add(s3Util.getObject(photo.getAmazonS3Key()));
        }

        String[] phoStrings = new String[photosBytes.size()];
        for (int i=0;i<photosBytes.size();i++) {
            phoStrings[i]=Base64.getEncoder().encodeToString(photosBytes.get(i));
        }

        return phoStrings;
    }
   


    public List<RealEstateRecentDTO> findRecentRealEstates(String username, Integer limit) 
    {
        return realEstateRepository.findRecentRealEstates(realEstateAgentRepository.findByUsername(username).get().getUserId(), limit);
    }


    public RealEstateAgentStatsDTO getAgentStats(String username) 
    {
        Integer[] estatesPerMonth = mockingStatsUtil.mockBarChartStats();
        RealEstateAgentStats realEstateAgentStats = realEstateAgentRepository.findByUsername(username).get().getRealEstateAgentStats();
        RealEstateAgentStatsDTO realEstateAgentStatsDTO = new RealEstateAgentStatsDTO(realEstateAgentStats, estatesPerMonth);
        return realEstateAgentStatsDTO;
    }

    
    public List<RealEstateStatsDTO> getRealEstateStats(String username, Pageable page) 
    {
        return realEstateRepository.findRealEstateStats(realEstateAgentRepository.findByUsername(username).get().getUserId(), 
                                                        page);
    }

    public Integer[] getBarChartStats() 
    {
        return mockingStatsUtil.mockBarChartStats();
    }
}