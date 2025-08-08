
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.entity.Agency;


@Repository
public interface AgencyRepository extends JpaRepository<Agency,Long> 
{
    public Optional<Agency> findByBusinessNameOrVatNumber(String businessName, String vatNumber);
}