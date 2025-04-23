
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.RealEstateAgent;



@Repository
public interface RealEstateAgentRepository extends JpaRepository<RealEstateAgent,Long> 
{
    public Optional<RealEstateAgent> findByUsername(String username);
}