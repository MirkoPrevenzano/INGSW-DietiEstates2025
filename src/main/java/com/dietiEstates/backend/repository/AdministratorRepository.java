
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.Administrator;


@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> 
{
   Optional<Administrator> findByUsername(String username);
}