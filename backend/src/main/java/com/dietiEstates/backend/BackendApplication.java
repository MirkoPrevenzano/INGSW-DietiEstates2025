
package com.dietiEstates.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.service.AgencyService;
import com.dietiEstates.backend.dto.request.AgencyRegistrationDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BackendApplication 
{
    private final PasswordEncoder passwordEncoder;
    
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AdministratorRepository administratorRepository, 
                                        AgentRepository agentRepository, UserRepository userRepository,
                                        RealEstateRepository realEstateRepository, MockingStatsService mockingStatsService,
                                        AgentService agentService,
                                        AgencyService agencyService)
    {
        return args -> 
        {  
            Administrator administrator = new Administrator("w", "x", "ydk", "jssssssssssssssssssss22A@");
            administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            administrator = administratorRepository.saveAndFlush(administrator);

  
            Agent agent = (new Agent("a","b","c",passwordEncoder.encode("ssssssssssssssssssss22A@")));
            mockingStatsService.mockAgentStats(agent);
            administrator.addAgent(agent);
            administrator = administratorRepository.save(administrator);    
            AgencyRegistrationDto agentRegistrationDto = new AgencyRegistrationDto("a", "b", "12345678901", "ciro","Pizza", "ciropizza2002@gmail.com","CiroPizza1926!");
            agencyService.createAgency(agentRegistrationDto); 
        };
    }
}