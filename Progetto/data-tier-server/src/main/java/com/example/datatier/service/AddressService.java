package com.example.datatier.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.model.Address;
import com.example.datatier.model.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressService(AddressRepository addressRepository,
                            ModelMapper modelMapper)
    {
        this.addressRepository=addressRepository;
        this.modelMapper=modelMapper;
    }

    public Address save(AddressDTO addressDTO)
    {
        Address address=modelMapper.map(addressDTO,Address.class);
        return addressRepository.save(address);
    }

}
