
package com.dietiestates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dietiestates.backend.model.entity.Administrator;


@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> 
{
    public Optional<Administrator> findByUsername(String username);

    
    @Query("SELECT a.agency.agencyName " +
           "FROM Administrator a " + 
           "WHERE a.username = :username")
    public String findAgencyNameByUsername(@Param("username") String username);
}