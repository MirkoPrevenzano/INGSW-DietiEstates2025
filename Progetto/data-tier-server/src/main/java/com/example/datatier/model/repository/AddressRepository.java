package com.example.datatier.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datatier.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
