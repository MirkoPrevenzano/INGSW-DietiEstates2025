
package com.dietiEstates.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.RealEstateForRent;


@Repository
public interface RealEstateForRentRepository extends JpaRepository<RealEstateForRent,Long> 
{
}