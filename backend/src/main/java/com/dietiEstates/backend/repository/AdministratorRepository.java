
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.entity.Administrator;



@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> 
{
    public Optional<Administrator> findByUsername(String username);
}