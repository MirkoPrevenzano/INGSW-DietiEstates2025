
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.entity.Agent;


@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> 
{
    public Optional<Agent> findByUsername(String username);

   @Query("SELECT a.administrator.agency.agencyName " +
          "FROM Agent a " +
          "WHERE a.username = :username")
    public String findAgencyNameByUsername(@Param("username") String username);
}