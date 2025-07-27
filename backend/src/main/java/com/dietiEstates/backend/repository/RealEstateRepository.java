
package com.dietiEstates.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.criteria.RealEstateCriteriaRepository;


@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long>, RealEstateCriteriaRepository
{
}