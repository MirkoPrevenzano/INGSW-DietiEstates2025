
package com.dietiEstates.backend;

import org.springframework.boot.CommandLineRunner;
/*import java.time.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import com.dietiEstates.backend.model.Address;
import com.dietiEstates.backend.model.Administrator;
import com.dietiEstates.backend.model.Customer;
import com.dietiEstates.backend.model.Photo;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.Agent;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import com.dietiEstates.backend.model.User;
import com.dietiEstates.backend.model.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.RealEstateStats;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.repository.AdministratorRepository;
import com.dietiEstates.backend.model.repository.CustomerRepository;
import com.dietiEstates.backend.model.repository.AgentRepository;
import com.dietiEstates.backend.model.repository.RealEstateForRentRepository;
import com.dietiEstates.backend.model.repository.RealEstateForSaleRepository;
import com.dietiEstates.backend.model.repository.RealEstateRepository;

import jakarta.transaction.Transactional;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiEstates.backend.helper.JFreeChartUtil;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agency;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.User;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.util.AmazonS3Util;
import com.dietiEstates.backend.util.ValidationUtil;

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
                                        AmazonS3Util s3Util, AgentRepository agentRepository, UserRepository userRepository,
                                        RealEstateRepository realEstateRepository,
                                        AgentService agentService,
                                        JFreeChartUtil jFreeChartUtil)
    {
        return args -> 
        {  
             Administrator administrator = new Administrator("w", "x", "ydk", "jssssssssssssssssssss22A@");
            administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            administrator.addAgency(new Agency("a", "null", "sdks"));
            administrator = administratorRepository.saveAndFlush(administrator);

  
            Agent agent = (new Agent("a","b","c",passwordEncoder.encode("ssssssssssssssssssss22A@")));
            administrator.addAgent(agent);
            administrator = administratorRepository.save(administrator);
            
           /* System.out.println("\n\nFINDING BY USERNAME BY USER REPO:");
            User user = userRepository.findByUsername("ydk").get();


            System.out.println("\n\nSAVING USER BY USER REPO WITHOUT UPDATE:");
            administratorRepository.save((Administrator)user);


            System.out.println("\n\nFINDING BY USERNAME BY ADMIN REPO:");
            Administrator admin = administratorRepository.findByUsername("ydk").get();


            System.out.println("\n\nSAVING USER BY ADMIN REPO WITHOUT UPDATE:");
            administratorRepository.save(admin);
             */
           /*administrator.getAgents().get(0).getAgentStats().setTotalUploadedRealEstates(200);
            administrator.getAgents().get(0).getAgentStats().setTotalRentedRealEstates(20);;
            administrator.getAgents().get(0).getAgentStats().setTotalSoldRealEstates(45);
            administrator.getAgents().get(0).getAgentStats().setRentalsIncome(1.60000);
            administrator.getAgents().get(0).getAgentStats().setSalesIncome(20.90000);

            administratorRepository.save(administrator);  

            jFreeChartService.createPieChart(administrator.getAgents().get(0));
            jFreeChartService.createPieChart2(administrator.getAgents().get(0));
            jFreeChartService.createBarChart();*/
/*             
            InternalRealEstateFeatures internalRealEstateFeatures = new InternalRealEstateFeatures(20.0, 5, 
                                                                                              "cecececec", "cecececec");
            ExternalRealEstateFeatures externalRealEstateFeatures = new ExternalRealEstateFeatures(2,6);
            RealEstateForSale realEstate = new RealEstateForSale("cecececec", "a", LocalDateTime.now(), 20.5, 26.7, 
                                                                "cecececec", internalRealEstateFeatures, externalRealEstateFeatures, "cecececec");
            RealEstateForRent realEstate2 = new RealEstateForRent("cecececec", "a", LocalDateTime.now(), 22.8, 26.7, "cecececec",
                                                                  internalRealEstateFeatures, externalRealEstateFeatures, 43.1, 5);
            agent.addRealEstate(realEstate);
            agent.addRealEstate(realEstate2);
            agentRepository.save(agent);



            Agent agent2 = (new Agent("a","b","cc",passwordEncoder.encode("ssssssssssssssssssss22A@")));
            agent2.setAdministrator(administrator);
            RealEstateForSale realEstate3 = new RealEstateForSale("cecececec", "a", LocalDateTime.now(), 20.5, 26.7, 
                                                                "cecececec", internalRealEstateFeatures, externalRealEstateFeatures, "cecececec");
            RealEstateForRent realEstate4 = new RealEstateForRent("cecececec", "a", LocalDateTime.now(), 22.8, 26.7, "cecececec",
                                                                  internalRealEstateFeatures, externalRealEstateFeatures, 43.1, 5);
            agent2.addRealEstate(realEstate3);
            agent2.addRealEstate(realEstate4);
            agentRepository.save(agent2); */

            // log.info(new String(foo));
        };
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(AdministratorRepository administratorRepository,
                                               AgentRepository agentRepository,
                                               CustomerRepository customerRepository,
                                               RealEstateForRentRepository realEstateForRentRepository,
                                               RealEstateForSaleRepository realEstateForSaleRepository,
                                               RealEstateRepository realEstateRepository) 
    {
        return args -> 
        {
            System.out.println();
            System.out.println();
            System.out.println("*** TEST RELAZIONE ADMIN-ADMIN ***");
            System.out.println();

            User user = new Administrator("lwlwlwl", "lwlwlwl", "lwlwlwl", 
                                          "lwlwlwl", "lwlwlwl");
            Administrator admin = (Administrator)user;


            System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");


            System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
            Administrator admin3 = new Administrator("lalalal","lalalal","lalalal",
            "lalalal", admin.getAgencyName());
            admin3.addCollaborator(new Administrator("lelelel","lelelel","lelelel",
            "lelelel", admin.getAgencyName()));
            administratorRepository.save(admin3);


            System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           admin3.addCollaborator(new Administrator("ldldldl","ldldldl","ldldldl",
           "ldldldl", admin.getAgencyName()));
           Administrator admin3Updated = administratorRepository.save(admin3);

           
           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");
           admin4.getCollaborators().clear();
           administratorRepository.deleteById(5l); //funziona con orphanRemoval = true


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY - OK");
           admin3Updated.removeCollaborator(administratorRepository.findById(6l).get());
           administratorRepository.deleteById(6l);



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE ADMIN-AGENTI ***");
           System.out.println();

           User user2 = new Agent("bebebeb", "bebebeb", "bebebeb", "bebebeb");
           Agent agent = (Agent)user2;


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
           Administrator admin5 = new Administrator("bababab","bababab","bababab", 
                                                    "bababab", "bababab");
           admin5.addAgent(agent);
           administratorRepository.save(admin5);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           Agent agent2 = new Agent("bibibib", "bibibib", 
                                                                  "bibibib", "bibibib");
           admin5.addAgent(agent2);
           admin5 = administratorRepository.save(admin5);


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - OK");
           administratorRepository.deleteById(7l); // funziona con orphanRemoval = true
           admin5.getAgents().clear(); // bisogna settare a null anche gli admin presenti negli oggetti agent...



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE AGENTE-IMMOBILI ***");
           System.out.println();


           User user3 = new Agent("cicicic", "cicicic", "cicicic", 
                                            "cicicic");
           Agent agent3 = (Agent)user3;
           agent3.setAdministrator(admin3Updated);


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
           InternalRealEstateFeatures internalRealEstateFeatures = new InternalRealEstateFeatures(20.0, 5, 
                                                                                                  "cecececec", "cecececec");
           ExternalRealEstateFeatures externalRealEstateFeatures = new ExternalRealEstateFeatures(2,6);
           RealEstateForSale realEstate = new RealEstateForSale("cecececec", Date.now(), 26.7, "cecececec",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, "cecececec");
           agent3.addRealEstate(realEstate);
           agentRepository.save(agent3);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           RealEstateForRent realEstate2 = new RealEstateForRent("cecececec", Date.now(), 26.7, "cecececec",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, 15.3, 6);           
           agent3.addRealEstate(realEstate2);
           agent3 = agentRepository.save(agent3);


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY - OK");
           //agentRepository.delete(agent3);  // funziona con orphanRemoval = true
           //agent3.getRealEstates().clear(); // bisogna settare a null anche gli agenti presenti negli oggetti realEstate...



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE CLIENTI-IMMOBILI ***");
           System.out.println();

           User user4 = new Customer("dedededed", "dedededed", "dedededed", "dedededed");
           Customer customer = (Customer) user4;


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY (CUSTOMER) - OK");
           RealEstateForSale realEstate3 = new RealEstateForSale("dadadadad", Date.now(), 26.7, "dadadadad",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, "dadadadad");   
           realEstate3.setAgent(agent3);       
           CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(Date.now(), customer, realEstate3);
           customer.addCustomerViewsRealEstate(customerViewsRealEstate);
           customer = customerRepository.save(customer);

           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY (CUSTOMER) - OK");
           CustomerViewsRealEstate customerViewsRealEstate2 = new CustomerViewsRealEstate(Date.now(), customer, realEstate);
           customer.addCustomerViewsRealEstate(customerViewsRealEstate2);
           customer = customerRepository.save(customer);

           
           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY (CUSTOMER) - OK");
           // va fatto prima della delete, in abbinamento a orphanremoval = true
           //customer.getCustomerViewsRealEstates().clear(); // bisogna elminare anche i customer presenti negli oggetti realEstate...           
           //customerRepository.delete(customer);
           //customerRepository.deleteById(1l); // funziona anche senza orphanremoval = true


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY (REALESTATE) - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY (REALESTATE) - NON NECESSARIO");

           
           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY (REALESTATE) - OK");
           realEstate3.getCustomerViewsRealEstates().clear();
           realEstateForSaleRepository.delete(realEstate3); // stesso discorso del caso di CUSTOMER



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE IMMOBILE-INDIRIZZO ***");
           System.out.println();

           RealEstateForSale realEstate4 = new RealEstateForSale("elelelelel", Date.now(), 26.7, "elelelel",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, "elelelelel");            
           realEstate4.setAgent(agent3);    
           
                                                         
           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETONE (REAL ESTATE) - OK");
           realEstate4.addAddress(new Address("ebebebebeb", "ebebebebeb", "ebebebebeb", 
                                              "ebebebebeb", "123456", 123));
           realEstateForSaleRepository.save(realEstate4);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETONE (REAL ESTATE) - OK");
           realEstate4.removeAddress();
           realEstate4 = realEstateForSaleRepository.save(realEstate4); // occorre usare orphanremoval = true per eliminare il vecchio address 

           realEstate4.addAddress(new Address("ececececec", "ececececec", "ececececec", 
                                              "ececececec", "123", 123));
           realEstate4 = realEstateForSaleRepository.save(realEstate4);
           

           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETONE (REAL ESTATE) - OK");
           realEstateForSaleRepository.deleteById(4l);
           //realEstateForSaleRepository.delete(realEstate4); // le due modalità fanno esattamente la stessa cosa


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETONE (ADDRESS) - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETONE (ADDRESS) - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETONE (ADDRESS) - NON NECESSARIO");



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE IMMOBILE-FOTOGRAFIE ***");
           System.out.println();
           
           RealEstateForSale realEstate5 = new RealEstateForSale("fafafafaf", Date.now(), 26.7, "fafafafaf",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, "fafafafaf");            
           realEstate5.setAgent(agent3);  


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
           List<Photo> p = new ArrayList<>();
           p.add(new Photo("fefefefef"));
           p.add(new Photo("fifififif"));
           realEstate5.setPhotos(p);
           realEstateForSaleRepository.save(realEstate5);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           realEstate5.getPhotos().add(new Photo("fofofofof"));
           realEstate5 = realEstateForSaleRepository.save(realEstate5);

           
           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY - OK");
           realEstateForSaleRepository.delete(realEstate5);
        };
    }*/
}
