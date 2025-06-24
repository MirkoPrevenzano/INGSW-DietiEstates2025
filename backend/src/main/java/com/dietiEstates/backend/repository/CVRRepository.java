
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;



@Repository
public interface CVRRepository extends JpaRepository<CustomerViewsRealEstate,CustomerViewsRealEstateId> 
{
}