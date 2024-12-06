package com.example.datatier.model.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datatier.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    Optional<Customer> findByEmail(String email);
    
}
