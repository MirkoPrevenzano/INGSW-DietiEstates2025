
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dietiEstates.backend.dto.RealEstateCompleteDTO;
import com.dietiEstates.backend.model.Customer;
import com.dietiEstates.backend.model.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService 
{
    private final RealEstateRepository realEstateRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    public RealEstateCompleteDTO viewRealEstate(String username, Long realEstateId) 
    {
        Customer customer = customerRepository.findByUsername(username).get();
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();        
                
        RealEstateCompleteDTO realEstateCompleteDTO = modelMapper.map(realEstate, RealEstateCompleteDTO.class);

        long newViewsNumber = realEstate.getRealEstateStats().getViewsNumber() + 1;
        realEstate.getRealEstateStats().setViewsNumber(newViewsNumber);

        CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(
                                                                new CustomerViewsRealEstateId(customer.getUserId(), realEstate.getRealEstateId()), 
                                                                LocalDateTime.now(), 
                                                                customer,
                                                                realEstate);

        List<CustomerViewsRealEstate> customerViewsRealEstateList = customer.getCustomerViewsRealEstates();

        if(customerViewsRealEstateList.size() > 0)
        {
            for(CustomerViewsRealEstate customerViewsRealEstate2 : customerViewsRealEstateList)
            {
                if(customerViewsRealEstate.getCustomerViewsRealEstateId().equals(customerViewsRealEstate2.getCustomerViewsRealEstateId()))
                {
                    customerViewsRealEstate2.setViewDate(LocalDateTime.now());
                    customerRepository.save(customer);
                    return realEstateCompleteDTO;
                }  
            }
        }
        
        customer.addCustomerViewsRealEstate(customerViewsRealEstate);
        customerRepository.save(customer);

        return realEstateCompleteDTO;
    }
}
