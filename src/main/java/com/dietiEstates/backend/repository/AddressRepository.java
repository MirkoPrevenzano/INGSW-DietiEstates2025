
package com.dietiEstates.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address,Long>
{    
}