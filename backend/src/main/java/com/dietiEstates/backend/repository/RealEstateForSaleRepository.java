
package com.dietiEstates.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.RealEstateForSale;


@Repository
public interface RealEstateForSaleRepository extends JpaRepository<RealEstateForSale,Long> 
{
}