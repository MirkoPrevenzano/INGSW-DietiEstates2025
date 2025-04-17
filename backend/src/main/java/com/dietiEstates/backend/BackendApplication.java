
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
import com.dietiEstates.backend.model.RealEstateAgent;
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
import com.dietiEstates.backend.model.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.model.repository.RealEstateForRentRepository;
import com.dietiEstates.backend.model.repository.RealEstateForSaleRepository;
import com.dietiEstates.backend.model.repository.RealEstateRepository;

import jakarta.transaction.Transactional;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiEstates.backend.model.Administrator;
import com.dietiEstates.backend.model.RealEstateAgent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.JFreeChartService;
import com.dietiEstates.backend.service.RealEstateAgentService;
import com.dietiEstates.backend.service.S3Service;

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
                                        S3Service s3Service, RealEstateAgentRepository realEstateAgentRepository,
                                        RealEstateRepository realEstateRepository,
                                        RealEstateAgentService realEstateAgentService,
                                        JFreeChartService jFreeChartService)
    {
        return args -> 
        {  
             Administrator administrator = new Administrator("w", "x", "ydk", "jssssssssssssssssssss22A@", "ak");
            administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            administrator = administratorRepository.save(administrator);

/* 
            RealEstateAgent agent = (new RealEstateAgent("a","b","c",passwordEncoder.encode("ssssssssssssssssssss22A@")));
            agent.setAdministrator(administrator);
            administrator.addRealEstateAgent(agent);
            administrator = administratorRepository.save(administrator);

            administrator.getRealEstateAgents().get(0).getRealEstateAgentStats().setTotalUploadedRealEstates(200);
            administrator.getRealEstateAgents().get(0).getRealEstateAgentStats().setTotalRentedRealEstates(20);;
            administrator.getRealEstateAgents().get(0).getRealEstateAgentStats().setTotalSoldRealEstates(45);
            administrator.getRealEstateAgents().get(0).getRealEstateAgentStats().setRentalsIncome(1.60000);
            administrator.getRealEstateAgents().get(0).getRealEstateAgentStats().setSalesIncome(20.90000);

            administratorRepository.save(administrator);  

            jFreeChartService.createPieChart(administrator.getRealEstateAgents().get(0));
            jFreeChartService.createPieChart2(administrator.getRealEstateAgents().get(0));
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
            realEstateAgentRepository.save(agent);



            RealEstateAgent agent2 = (new RealEstateAgent("a","b","cc",passwordEncoder.encode("ssssssssssssssssssss22A@")));
            agent2.setAdministrator(administrator);
            RealEstateForSale realEstate3 = new RealEstateForSale("cecececec", "a", LocalDateTime.now(), 20.5, 26.7, 
                                                                "cecececec", internalRealEstateFeatures, externalRealEstateFeatures, "cecececec");
            RealEstateForRent realEstate4 = new RealEstateForRent("cecececec", "a", LocalDateTime.now(), 22.8, 26.7, "cecececec",
                                                                  internalRealEstateFeatures, externalRealEstateFeatures, 43.1, 5);
            agent2.addRealEstate(realEstate3);
            agent2.addRealEstate(realEstate4);
            realEstateAgentRepository.save(agent2); */

            // log.info(new String(foo));
        };
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(AdministratorRepository administratorRepository,
                                               RealEstateAgentRepository realEstateAgentRepository,
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

           User user2 = new RealEstateAgent("bebebeb", "bebebeb", "bebebeb", "bebebeb");
           RealEstateAgent realEstateAgent = (RealEstateAgent)user2;


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
           Administrator admin5 = new Administrator("bababab","bababab","bababab", 
                                                    "bababab", "bababab");
           admin5.addRealEstateAgent(realEstateAgent);
           administratorRepository.save(admin5);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           RealEstateAgent realEstateAgent2 = new RealEstateAgent("bibibib", "bibibib", 
                                                                  "bibibib", "bibibib");
           admin5.addRealEstateAgent(realEstateAgent2);
           admin5 = administratorRepository.save(admin5);


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - OK");
           administratorRepository.deleteById(7l); // funziona con orphanRemoval = true
           admin5.getRealEstateAgents().clear(); // bisogna settare a null anche gli admin presenti negli oggetti realEstateAgent...



           System.out.println();
           System.out.println();
           System.out.println();
           System.out.println("*** TEST RELAZIONE AGENTE-IMMOBILI ***");
           System.out.println();


           User user3 = new RealEstateAgent("cicicic", "cicicic", "cicicic", 
                                            "cicicic");
           RealEstateAgent realEstateAgent3 = (RealEstateAgent)user3;
           realEstateAgent3.setAdministrator(admin3Updated);


           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE MANYTOONE - NON NECESSARIO");
           

           System.out.println("CASCADE TYPE PERSIST DA RELAZIONE ONETOMANY - OK");
           InternalRealEstateFeatures internalRealEstateFeatures = new InternalRealEstateFeatures(20.0, 5, 
                                                                                                  "cecececec", "cecececec");
           ExternalRealEstateFeatures externalRealEstateFeatures = new ExternalRealEstateFeatures(2,6);
           RealEstateForSale realEstate = new RealEstateForSale("cecececec", Date.now(), 26.7, "cecececec",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, "cecececec");
           realEstateAgent3.addRealEstate(realEstate);
           realEstateAgentRepository.save(realEstateAgent3);


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE MERGE DA RELAZIONE ONETOMANY - OK");
           RealEstateForRent realEstate2 = new RealEstateForRent("cecececec", Date.now(), 26.7, "cecececec",
                                                         internalRealEstateFeatures, externalRealEstateFeatures, 
                                                         20.5, 15.3, 6);           
           realEstateAgent3.addRealEstate(realEstate2);
           realEstateAgent3 = realEstateAgentRepository.save(realEstateAgent3);


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE MANYTOONE - NON NECESSARIO");


           System.out.println("CASCADE TYPE REMOVE DA RELAZIONE ONETOMANY - OK");
           //realEstateAgentRepository.delete(realEstateAgent3);  // funziona con orphanRemoval = true
           //realEstateAgent3.getRealEstates().clear(); // bisogna settare a null anche gli agenti presenti negli oggetti realEstate...



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
           realEstate3.setRealEstateAgent(realEstateAgent3);       
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
           realEstate4.setRealEstateAgent(realEstateAgent3);    
           
                                                         
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
           realEstate5.setRealEstateAgent(realEstateAgent3);  


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