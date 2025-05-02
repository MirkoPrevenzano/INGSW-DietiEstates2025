
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
import com.dietiEstates.backend.controller.CSVController;
import com.dietiEstates.backend.dto.RealEstateAgentStatsDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.EstateCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.Address;
import com.dietiEstates.backend.model.Photo;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.RealEstateAgent;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.RealEstateAgentStats;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;

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
    private final S3Service s3Service;
    private final MockingStatsService mockingStatsService;
    private final ModelMapper modelMapper;
    private final ValidatorService validatorService;



    @Transactional
    public Long createRealEstateForSale(String username, RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)  throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> realEstateAgentOptional = realEstateAgentRepository.findByUsername(username);
        if(realEstateAgentOptional.isEmpty())
        {
            log.error("Agent not found in database");
            throw new UsernameNotFoundException("Agent not found in database");
        }
        RealEstateAgent realEstateAgent = realEstateAgentOptional.get();
        
        RealEstateForSale realEstateForSale = realEstateForSaleMapper(realEstateForSaleCreationDTO);

        Address address = modelMapper.map(realEstateForSaleCreationDTO.getAddressDTO(), Address.class);
        realEstateForSale.addAddress(address);

        mockingStatsService.mockEstateStats(realEstateForSale);

        realEstateAgent.addRealEstate(realEstateForSale);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        realEstateAgent.addRealEstate(realEstateForSale);

        realEstateAgent = realEstateAgentRepository.save(realEstateAgent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastRealEstate(realEstateAgent.getUserId());
    }


    @Transactional
    public Long createRealEstateForRent(String username, RealEstateForRentCreationDTO realEstateForRentCreationDTO) throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> realEstateAgentOptional = realEstateAgentRepository.findByUsername(username);
        if(realEstateAgentOptional.isEmpty())
        {
            log.error("Agent not found in database");
            throw new UsernameNotFoundException("Agent not found in database");
        }
        RealEstateAgent realEstateAgent = realEstateAgentOptional.get();

        RealEstateForRent realEstateForRent = realEstateForRentMapper(realEstateForRentCreationDTO);

        Address address = modelMapper.map(realEstateForRentCreationDTO.getAddressDTO(), Address.class);
        realEstateForRent.addAddress(address);

        mockingStatsService.mockEstateStats(realEstateForRent);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        realEstateAgent.addRealEstate(realEstateForRent);
        
        realEstateAgent = realEstateAgentRepository.save(realEstateAgent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastRealEstate(realEstateAgent.getUserId());
    }


    public void uploadPhoto(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException
    {
        Optional<RealEstate> realEstateOptional = realEstateRepository.findById(realEstateId);
        if(realEstateOptional.isEmpty())
        {
            log.error("Real Estate not found in database");
            throw new IllegalArgumentException("Real Estate not found in database");
        }
        RealEstate realEstate = realEstateOptional.get();
        
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
                s3Service.putObject("%s".formatted(photoKey), multipartFile.getBytes());
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
            photosBytes.add(s3Service.getObject(photo.getAmazonS3Key()));
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
        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats();
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
        return mockingStatsService.mockBarChartStats();
    }



    private RealEstateForRent realEstateForRentMapper(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        String title = realEstateForRentCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForRentCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForRentCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForRentCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validatorService.enumValidator(EnergyClass.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        Double securityDeposit = realEstateForRentCreationDTO.getSecurityDeposit();
        Integer contractYears = realEstateForRentCreationDTO.getContractYears();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validatorService.enumValidator(EstateCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validatorService.enumValidator(FurnitureCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getHeating());

        externalRealEstateFeatures.setElevator(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getElevator());
        externalRealEstateFeatures.setConcierge(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getTerrace());
        externalRealEstateFeatures.setGarage(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarage());
        externalRealEstateFeatures.setBalcony(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getBalcony());
        externalRealEstateFeatures.setGarden(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());
    
        return new RealEstateForRent(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, securityDeposit, contractYears);
    }

    private RealEstateForSale realEstateForSaleMapper(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        String title = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validatorService.enumValidator(EnergyClass.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        NotaryDeedState notaryDeedState = validatorService.enumValidator(NotaryDeedState.class, realEstateForSaleCreationDTO.getNotaryDeedState());

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validatorService.enumValidator(EstateCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validatorService.enumValidator(FurnitureCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getHeating());

        externalRealEstateFeatures.setElevator(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getElevator());
        externalRealEstateFeatures.setConcierge(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getTerrace());
        externalRealEstateFeatures.setGarage(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarage());
        externalRealEstateFeatures.setBalcony(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getBalcony());
        externalRealEstateFeatures.setGarden(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());

        return new RealEstateForSale(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, notaryDeedState);
    }
}
