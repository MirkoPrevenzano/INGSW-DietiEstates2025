
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
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.dto.response.AgentDashboardPersonalStatsDTO;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDTO;
import com.dietiEstates.backend.dto.response.support.AgentStatsDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
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
import com.dietiEstates.backend.resolver.RealEstateFactoryFromDTOResolver;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.service.photo.PhotoService;

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
    private final MockingStatsService mockingStatsService;
    private final ModelMapper modelMapper;
    //private final ValidationUtil validationUtil;
    private final RealEstateFactoryFromDTOResolver realEstateFactoryFromDTOResolver;
    private final PhotoService photoService;



    @Transactional
    public Long createRealEstate(String username, RealEstateCreationDTO realEstateCreationDTO)  throws UsernameNotFoundException
    {
        Optional<Agent> optionalRealEstateAgent = agentRepository.findByUsername(username);
        Agent agent = optionalRealEstateAgent.get();
        
        RealEstateFromDtoFactory realEstateFromDtoFactory = realEstateFactoryFromDTOResolver.getFactory(realEstateCreationDTO);
        RealEstate realEstate = realEstateFromDtoFactory.create(realEstateCreationDTO);

        Address address = modelMapper.map(realEstateCreationDTO.getAddressDTO(), Address.class);
        realEstate.addAddress(address);

        mockingStatsService.mockEstateStats(realEstate);

        agent.addRealEstate(realEstate);

        int newTotalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates() + 1;
        agent.getAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        agent.addRealEstate(realEstate);

        agent = agentRepository.save(agent);

        log.info("Real Estate For Sale was created successfully!");

        return realEstateRepository.findLastUploadedByAgent(agent.getUserId());
    }



    public void uploadPhoto2(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException, IOException
    {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate realEstate = optionalRealEstate.get();
        
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


    public List<String> getPhoto2(Long realEstateId) throws IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();

        List<Photo> photos = realEstate.getPhotos();
/*         if (photos == null || photos.isEmpty()) {
            return new String[]{};
        }
         */
        List<String> photosBase64 = new ArrayList<>();

        for(Photo photo : photos)
        {
            photosBase64.add(photoService.getPhotoAsBase64(photo.getKey()));
        }

        return photosBase64;

/*         String[] phoStrings = new String[photosBytes.size()];
        for (int i=0;i<photosBytes.size();i++) {
            phoStrings[i]=Base64.getEncoder().encodeToString(photosBytes.get(i));
        }

        return phoStrings; */
    }





/*     public void uploadPhoto(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException
    {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(realEstateId);
        RealEstate realEstate = optionalRealEstate.get();
        
        if(files.length < 3 || files.length > 10)
        {
            log.error("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
            throw new IllegalArgumentException("You have inserted a wrong number of photos! You must add from 3 to 10 photos.");
        } 

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
 */
    
/*     public String[] getPhoto(Long realEstateId) throws IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();

        List<Photo> photos = realEstate.getPhotos();
        if (photos == null || photos.isEmpty()) {
            return new String[]{};
        }
        
        List<byte[]> photosBytes = new ArrayList<>();

        for(Photo photo : photos)
        {
            photosBytes.add(amazonS3Util.getObject(photo.getKey()));
        }

        String[] phoStrings = new String[photosBytes.size()];
        for (int i=0;i<photosBytes.size();i++) {
            phoStrings[i]=Base64.getEncoder().encodeToString(photosBytes.get(i));
        }

        return phoStrings;
    } */
   


    public List<AgentRecentRealEstateDTO> findRecentRealEstates(String username, Integer limit) 
    {
        return realEstateRepository.findAgentRecentRealEstatesByAgent(agentRepository.findByUsername(username).get().getUserId(), limit);
    }





    public AgentDashboardPersonalStatsDTO getAgentDashboardStats(String username) 
    {
        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats();
        AgentStats agentStats = agentRepository.findByUsername(username).get().getAgentStats();

        AgentStatsDTO agentStatsDTO2 = new AgentStatsDTO();
        modelMapper.map(agentStats, agentStatsDTO2);


        AgentDashboardPersonalStatsDTO agentDashboardPersonalStatsDTO = new AgentDashboardPersonalStatsDTO(agentStatsDTO2, estatesPerMonth);
        return agentDashboardPersonalStatsDTO;
    }



/*     public AgentStatsDTO getAgentStats(String username) 
    {
        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats();
        AgentStats agentStats = agentRepository.findByUsername(username).get().getAgentStats();
        AgentStatsDTO agentStatsDTO = new AgentStatsDTO(agentStats, estatesPerMonth);
        return agentStatsDTO;
    }
 */
    
    public List<AgentDashboardRealEstateStatsDTO> getRealEstateStats(String username, Pageable page) 
    {
        return realEstateRepository.findAgentDashboardRealEstateStatsByAgent(agentRepository.findByUsername(username).get().getUserId(), 
                                                        page);
    }

    public Integer[] getBarChartStats() 
    {
        return mockingStatsService.mockBarChartStats();
    }
}