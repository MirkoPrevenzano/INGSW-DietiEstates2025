
package com.dietiEstates.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.RealEstateAgentStatsDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
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


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateAgentService 
{
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final RealEstateRepository realEstateRepository;
    private final S3Service s3Service;
    private final MockingStatsService mockingStatsService;


    @Transactional
    public void createRealEstateForSale(String username, RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)  throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> realEstateAgentOptional = realEstateAgentRepository.findByUsername(username);
        if(realEstateAgentOptional.isEmpty())
        {
            log.error("Agent not found in database");
            throw new UsernameNotFoundException("Agent not found in database");
        }
        RealEstateAgent realEstateAgent = realEstateAgentOptional.get();
        
        RealEstateForSale realEstateForSale = realEstateForSaleMapper(realEstateForSaleCreationDTO);
        Address address = realEstateForSaleCreationDTO.getAddress();
        realEstateForSale.addAddress(address);

        mockingStatsService.mockEstateStats(realEstateForSale);

        realEstateAgent.addRealEstate(realEstateForSale);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        realEstateAgent.addRealEstate(realEstateForSale);

        realEstateAgentRepository.save(realEstateAgent);

        log.info("Real Estate For Sale was created successfully!");
    }


    @Transactional
    public void createRealEstateForRent(String username, RealEstateForRentCreationDTO realEstateForRentCreationDTO) throws UsernameNotFoundException
    {
        Optional<RealEstateAgent> realEstateAgentOptional = realEstateAgentRepository.findByUsername(username);
        if(realEstateAgentOptional.isEmpty())
        {
            log.error("Agent not found in database");
            throw new UsernameNotFoundException("Agent not found in database");
        }
        RealEstateAgent realEstateAgent = realEstateAgentOptional.get();

        RealEstateForRent realEstateForRent = realEstateForRentMapper(realEstateForRentCreationDTO);
        Address address = realEstateForRentCreationDTO.getAddress();
        realEstateForRent.addAddress(address);

        mockingStatsService.mockEstateStats(realEstateForRent);

        int newTotalUploadedRealEstates = realEstateAgent.getRealEstateAgentStats().getTotalUploadedRealEstates() + 1;
        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);

        realEstateAgent.addRealEstate(realEstateForRent);
        realEstateAgentRepository.save(realEstateAgent);
        
        log.info("Real Estate For Rent was created successfully!");
    }


    public String uploadPhoto(String username, MultipartFile file, Long realEstateId) throws IllegalArgumentException, RuntimeException
    {
        Optional<RealEstate> realEstateOptional = realEstateRepository.findById(realEstateId);
        if(realEstateOptional.isEmpty())
        {
            log.error("Real Estate not found in database");
            throw new IllegalArgumentException("Real Estate not found in database");
        }
        RealEstate realEstate = realEstateOptional.get();

        String contentType = file.getContentType();
        Long size = file.getSize();

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
            s3Service.putObject("%s".formatted(photoKey), file.getBytes());
        } 
        catch (SdkException | IOException e) 
        {
            log.error("Amazon S3/SDK/IO exception has occurred while putting photo in the bucket!" + e.getMessage());
            throw new RuntimeException("Failed to upload photo!", e);
        }

        Photo photo = new Photo(photoKey);
        realEstate.getPhotos().add(photo);
        realEstateRepository.save(realEstate);
        
        return photoKey;
    }

    
    public byte[] getPhoto(String photoKey) throws IOException
    {
        // TODO: controllare se l'immobile ha foto

        return s3Service.getObject(photoKey);
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
        return realEstateRepository.findRealEstateStats2(realEstateAgentRepository.findByUsername(username).get().getUserId(), 
                                                        page);
    }

    public Integer[] getBarChartStats() 
    {
        return mockingStatsService.mockBarChartStats();
    }



    private RealEstateForRent realEstateForRentMapper(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        String title = realEstateForRentCreationDTO.getEstateDescribe().getTitle();
        String description = realEstateForRentCreationDTO.getEstateDescribe().getDescription();
        Double price = realEstateForRentCreationDTO.getEstateDescribe().getPrice();
        Double condoFee = realEstateForRentCreationDTO.getEstateDescribe().getCondoFee();
        String energyClass = realEstateForRentCreationDTO.getEstateDescribe().getEnergyClass();
        Double securityDeposit = realEstateForRentCreationDTO.getSecurityDeposit();
        Integer contractYears = realEstateForRentCreationDTO.getContractYears();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForRentCreationDTO.getEstateDescribe().getSize(), 
                                                                       realEstateForRentCreationDTO.getEstateDescribe().getRoomsNumber(), 
                                                                       realEstateForRentCreationDTO.getEstateDescribe().getEstateCondition(), 
                                                                       realEstateForRentCreationDTO.getEstateDescribe().getFurnitureCondition());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForRentCreationDTO.getEstateDescribe().getParkingSpacesNumber(), 
                                                                       realEstateForRentCreationDTO.getEstateDescribe().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForRentCreationDTO.getEstateFeatures().isHasAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForRentCreationDTO.getEstateFeatures().isHasHeating());

        externalRealEstateFeatures.setElevator(realEstateForRentCreationDTO.getEstateFeatures().isHasElevator());
        externalRealEstateFeatures.setConcierge(realEstateForRentCreationDTO.getEstateFeatures().isHasConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForRentCreationDTO.getEstateFeatures().isHasTerrace());
        externalRealEstateFeatures.setGarage(realEstateForRentCreationDTO.getEstateFeatures().isHasGarage());
        externalRealEstateFeatures.setBalcony(realEstateForRentCreationDTO.getEstateFeatures().isHasBalcony());
        externalRealEstateFeatures.setGarden(realEstateForRentCreationDTO.getEstateFeatures().isHasGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForRentCreationDTO.getEstateFeatures().isHasSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForRentCreationDTO.getEstateLocationFeatures().isNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForRentCreationDTO.getEstateLocationFeatures().isNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForRentCreationDTO.getEstateLocationFeatures().isNearPublicTransport());
    
        return new RealEstateForRent(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, securityDeposit, contractYears);
    }

    private RealEstateForSale realEstateForSaleMapper(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        String title = realEstateForSaleCreationDTO.getEstateDescribe().getTitle();
        String description = realEstateForSaleCreationDTO.getEstateDescribe().getDescription();
        Double price = realEstateForSaleCreationDTO.getEstateDescribe().getPrice();
        Double condoFee = realEstateForSaleCreationDTO.getEstateDescribe().getCondoFee();
        String energyClass = realEstateForSaleCreationDTO.getEstateDescribe().getEnergyClass();
        String notaryDeedState = realEstateForSaleCreationDTO.getNotaryDeedState();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForSaleCreationDTO.getEstateDescribe().getSize(), 
                                                                       realEstateForSaleCreationDTO.getEstateDescribe().getRoomsNumber(), 
                                                                       realEstateForSaleCreationDTO.getEstateDescribe().getEstateCondition(), 
                                                                       realEstateForSaleCreationDTO.getEstateDescribe().getFurnitureCondition());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForSaleCreationDTO.getEstateDescribe().getParkingSpacesNumber(), 
                                                                       realEstateForSaleCreationDTO.getEstateDescribe().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForSaleCreationDTO.getEstateFeatures().isHasAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForSaleCreationDTO.getEstateFeatures().isHasHeating());

        externalRealEstateFeatures.setElevator(realEstateForSaleCreationDTO.getEstateFeatures().isHasElevator());
        externalRealEstateFeatures.setConcierge(realEstateForSaleCreationDTO.getEstateFeatures().isHasConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForSaleCreationDTO.getEstateFeatures().isHasTerrace());
        externalRealEstateFeatures.setGarage(realEstateForSaleCreationDTO.getEstateFeatures().isHasGarage());
        externalRealEstateFeatures.setBalcony(realEstateForSaleCreationDTO.getEstateFeatures().isHasBalcony());
        externalRealEstateFeatures.setGarden(realEstateForSaleCreationDTO.getEstateFeatures().isHasGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForSaleCreationDTO.getEstateFeatures().isHasSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForSaleCreationDTO.getEstateLocationFeatures().isNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForSaleCreationDTO.getEstateLocationFeatures().isNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForSaleCreationDTO.getEstateLocationFeatures().isNearPublicTransport());

        return new RealEstateForSale(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, notaryDeedState);
    }
}
