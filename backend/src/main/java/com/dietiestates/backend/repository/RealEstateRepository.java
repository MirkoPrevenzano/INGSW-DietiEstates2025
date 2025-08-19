
package com.dietiestates.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.repository.criteria.RealEstateCriteriaRepository;


@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long>, RealEstateCriteriaRepository
{
}