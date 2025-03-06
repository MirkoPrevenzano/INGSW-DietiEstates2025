package com.example.datatier.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.datatier.model.PropertyRent;

public interface PropertyRentRepository extends JpaRepository<PropertyRent, Long>,JpaSpecificationExecutor<PropertyRent>{}
//JpaSpecification permette di definire delle query precise per i filtri
