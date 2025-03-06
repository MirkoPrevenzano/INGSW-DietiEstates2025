
package com.dietiEstates.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.model.RealEstateAgent;


@Repository
public interface RealEstateAgentRepository extends JpaRepository<RealEstateAgent,Long> 
{
    Optional<RealEstateAgent> findByUsername(String username);

/*     @Query(value = "SELECT new com.dietiEstates.backend.dto.RealEstateRecentDTO(re.id, re.title, re.description, re.uploadingDate)" +
                   "FROM RealEstate re " +
                   "WHERE re.realEstateAgent.userId = 2 " + 
                   "ORDER BY re.realEstateId DESC")
    List<RealEstateRecentDTO> findRecentRealEstates2(Long agentId, Limit limit);  */

}