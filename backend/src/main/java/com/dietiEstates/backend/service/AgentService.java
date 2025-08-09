
package com.dietiEstates.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.dto.response.AgentDashboardPersonalStatsDTO;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDTO;
import com.dietiEstates.backend.dto.response.support.AgentStatsDTO;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Photo;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.resolver.RealEstateFromDTOFactoryResolver;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.service.photo.PhotoData;
import com.dietiEstates.backend.service.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AgentService 
{
    private final AgentRepository agentRepository;
    private final RealEstateRepository realEstateRepository;
    private final MockingStatsService mockingStatsService;
    private final ModelMapper modelMapper;
    private final RealEstateFromDTOFactoryResolver realEstateFromDTOFactoryResolver;
    private final PhotoService photoService;



    // TODO: DA RIMUOVERE PER REST API, mettere in realEstateService
    @Transactional
    public Long createRealEstate(String username, RealEstateCreationDTO realEstateCreationDTO)  throws UsernameNotFoundException
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
        
        RealEstateFromDtoFactory realEstateFromDtoFactory = realEstateFromDTOFactoryResolver.getFactory(realEstateCreationDTO);
        RealEstate realEstate = realEstateFromDtoFactory.create(realEstateCreationDTO);

        Address address = modelMapper.map(realEstateCreationDTO.getAddressDTO(), Address.class);
        realEstate.addAddress(address);

        mockingStatsService.mockRealEstateStats(realEstate);

        agent.addRealEstate(realEstate);

        int newTotalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates() + 1;
        agent.getAgentStats().setTotalUploadedRealEstates(newTotalUploadedRealEstates);
        agent.addRealEstate(realEstate);

        agent = agentRepository.save(agent);

        log.info("Real Estate was created successfully!");

        return realEstateRepository.findLastUploadedByAgent(agent.getUserId());
    }



    @Transactional
    public void uploadPhoto2(String username, MultipartFile[] files, Long realEstateId) throws IllegalArgumentException, RuntimeException, IOException
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


    public List<PhotoData> getPhoto2(Long realEstateId) throws IOException
    {
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
                                                    .orElseThrow(() -> new UsernameNotFoundException(""));

        List<Photo> photos = realEstate.getPhotos();

        List<PhotoData> photosData = new ArrayList<>();
        for(Photo photo : photos)
            photosData.add(photoService.getPhotoAsBase64(photo.getKey()));


        return photosData;
    }


    public List<AgentRecentRealEstateDTO> getAgentRecentRealEstates(String username, Integer limit) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
                                     
        return realEstateRepository.findAgentRecentRealEstatesByAgent(agent.getUserId(), limit);
    }


    public List<AgentDashboardRealEstateStatsDTO> getAgentDashboardRealEstateStats(String username, Pageable page) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));

        return realEstateRepository.findAgentDashboardRealEstateStatsByAgent(agent.getUserId(), page);
    }


    public AgentDashboardPersonalStatsDTO getAgentDashboardPersonalStats(String username) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));

        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats(agent);
        AgentStatsDTO agentStatsDTO = modelMapper.map(agent.getAgentStats(), AgentStatsDTO.class);

        AgentDashboardPersonalStatsDTO agentDashboardPersonalStatsDTO = new AgentDashboardPersonalStatsDTO(agentStatsDTO, estatesPerMonth);
        
        return agentDashboardPersonalStatsDTO;
    }
}