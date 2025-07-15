
package com.dietiEstates.backend.repository.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.response.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.response.RealEstateStatsDTO;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;
import com.dietiEstates.backend.factory.RealEstateRootFactory;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.RealEstate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class RealEstateCriteriaRepositoryImpl implements RealEstateCriteriaRepository 
{
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public Page<RealEstatePreviewInfoDTO> findPreviewsByFilters(Map<String,String> filters, Pageable page, CoordinatesMinMax coordinatesMinMax) 
    {        
        CriteriaQuery<RealEstatePreviewInfoDTO> query = getPreviewsQueryByFilters(filters, coordinatesMinMax);
        List<RealEstatePreviewInfoDTO> pageList = entityManager.createQuery(query)
        .setFirstResult((int)page.getOffset())
        .setMaxResults(page.getPageSize())
        .getResultList(); 

        CriteriaQuery<Long> countQuery = getPreviewsCountQueryByFilters(filters, coordinatesMinMax);
        long totalElements = entityManager.createQuery(countQuery)
                                          .getFirstResult();       
        
        PageImpl<RealEstatePreviewInfoDTO> pageImpl = new PageImpl<>(pageList, page, totalElements);

        return pageImpl;
    }


    @Override
    public List<RealEstateRecentDTO> findRecentsByAgent(Long agentId, Integer limit) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstateRecentDTO> query = criteriaBuilder.createQuery(RealEstateRecentDTO.class);

        Root<RealEstate> realEstate = query.from(RealEstate.class);

        Path<Long> agentIdOfRealEstate = realEstate.get("agent").get("userId");

        query.select(criteriaBuilder.construct(RealEstateRecentDTO.class, realEstate.get("realEstateId"), 
                                                                                              realEstate.get("title"), 
                                                                                              realEstate.get("description"), 
                                                                                              realEstate.get("uploadingDate")))
                     .where(criteriaBuilder.equal(agentIdOfRealEstate, agentId))
                     .orderBy(criteriaBuilder.desc(realEstate.get("uploadingDate")));

        return entityManager.createQuery(query)
                            .setMaxResults(limit)
                            .getResultList();
    }


    @Override
    public List<RealEstateStatsDTO> findStatsByAgent(Long agentId, Pageable page) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstateStatsDTO> query = criteriaBuilder.createQuery(RealEstateStatsDTO.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);

        Path<Long> agentIdOfRealEstate = realEstate.get("agent").get("userId");

        query.select(criteriaBuilder.construct(RealEstateStatsDTO.class, realEstate.get("realEstateId"), 
                                                                                             realEstate.get("title"), 
                                                                                             realEstate.get("uploadingDate"), 
                                                                                             realEstate.get("realEstateStats").get("viewsNumber"),
                                                                                             realEstate.get("realEstateStats").get("visitsNumber"),
                                                                                             realEstate.get("realEstateStats").get("offersNumber")))
                    .where(criteriaBuilder.equal(agentIdOfRealEstate, agentId))
                    .orderBy(criteriaBuilder.asc(realEstate.get("realEstateId")));

        List<RealEstateStatsDTO> list;
        if(page != null)
        {
            list = entityManager.createQuery(query)
            .setFirstResult((int)page.getOffset())
            .setMaxResults(page.getPageSize())
            .getResultList();
        }
        else
        {
           list = entityManager.createQuery(query)
            .getResultList(); 
        }

        return list;
    }       
    

    @Override
    public Long findLastUploadedByAgent(Long agentId) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
    
        Path<Long> agentIdOfRealEstate = realEstate.get("agent").get("userId");

        query.select(realEstate.get("realEstateId"))
                     .where(criteriaBuilder.equal(agentIdOfRealEstate, agentId))
                     .orderBy(criteriaBuilder.desc(realEstate.get("realEstateId")));

        Long id = entityManager.createQuery(query)
                               .setMaxResults(1)
                               .getSingleResult();

        return id;
    }   

    

    
    private CriteriaQuery<RealEstatePreviewInfoDTO> getPreviewsQueryByFilters(Map<String,String> filters, CoordinatesMinMax coordinatesMinMax)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstatePreviewInfoDTO> query = criteriaBuilder.createQuery(RealEstatePreviewInfoDTO.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
        Join<RealEstate, Address> addressJoin = realEstate.join("address", JoinType.INNER);

        Root<?> realEstateType = RealEstateRootFactory.createFromType(filters.get("type"), query);

        List<Predicate> predicates = getPredicates(criteriaBuilder, filters, coordinatesMinMax, realEstate, addressJoin, realEstateType); 

        query.select(criteriaBuilder.construct(RealEstatePreviewInfoDTO.class, realEstate.get("realEstateId"),
                                                                                               realEstate.get("title"),
                                                                                               realEstate.get("description"),
                                                                                               realEstate.get("price"),
                                                                                               addressJoin.get("street"),
                                                                                               addressJoin.get("longitude"),
                                                                                               addressJoin.get("latitude")))
                     .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));    
        
        return query;
    }


    private CriteriaQuery<Long> getPreviewsCountQueryByFilters(Map<String,String> filters, CoordinatesMinMax coordinatesMinMax) 
    {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            
            Root<RealEstate> realEstate = countQuery.from(RealEstate.class);
            Join<RealEstate, Address> addressJoin = realEstate.join("address", JoinType.INNER);
            Root<?> realEstateType = RealEstateRootFactory.createFromType(filters.get("type"), countQuery);

            List<Predicate> predicates = getPredicates(criteriaBuilder, filters, coordinatesMinMax, realEstate, addressJoin, realEstateType);

            countQuery.select(criteriaBuilder.count(realEstate))
                         .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

/*             return entityManager.createQuery(countQuery)
                                .getSingleResult(); */
            return countQuery;
    }


    private List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Map<String, String> filters, CoordinatesMinMax coordinatesMinMax, 
                                          Root<RealEstate> realEstate, Join<RealEstate,Address> addressJoin, Root<?> realEstateType) 
    {
        Path<Long> realEstateId = realEstate.get("realEstateId");
        Path<Long> realEstateTypeId = realEstateType.get("realEstateId");

        Path<String> energyClass = realEstate.get("energyClass");
        Path<Double> price = realEstate.get("price");     
        Path<Integer> roomsNumber = realEstate.get("internalFeatures").get("roomsNumber");
        Path<Boolean> airConditioning = realEstate.get("internalFeatures").get("airConditioning");
        Path<Boolean> heating = realEstate.get("internalFeatures").get("heating");
        Path<Boolean> elevator = realEstate.get("externalFeatures").get("elevator");
        Path<Boolean> concierge = realEstate.get("externalFeatures").get("concierge");
        Path<Boolean> terrace = realEstate.get("externalFeatures").get("terrace");
        Path<Boolean> garage = realEstate.get("externalFeatures").get("garage");
        Path<Boolean> balcony = realEstate.get("externalFeatures").get("balcony");
        Path<Boolean> garden = realEstate.get("externalFeatures").get("garden");
        Path<Boolean> swimmingPool = realEstate.get("externalFeatures").get("swimmingPool");
        Path<Boolean> nearPark = realEstate.get("externalFeatures").get("nearPark");
        Path<Boolean> nearSchool = realEstate.get("externalFeatures").get("nearSchool");
        Path<Boolean> nearPublicTransport = realEstate.get("externalFeatures").get("nearPublicTransport");
        Path<Double> latitude = addressJoin.get("latitude");
        Path<Double> longitude = addressJoin.get("longitude");   


        List<Predicate> predicates = new ArrayList<>();
            
        predicates.add(criteriaBuilder.equal(realEstateId, realEstateTypeId));

        predicates.add(criteriaBuilder.ge(latitude, coordinatesMinMax.getMinLatitude()));
        predicates.add(criteriaBuilder.le(latitude, coordinatesMinMax.getMaxLatitude()));
        predicates.add(criteriaBuilder.le(longitude, coordinatesMinMax.getMinLongitude()));
        predicates.add(criteriaBuilder.ge(longitude, coordinatesMinMax.getMaxLongitude()));
              
        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            if(entry.getKey().equals("minPrice"))
            {
                predicates.add(criteriaBuilder.ge(price, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("maxPrice"))
            {
                predicates.add(criteriaBuilder.le(price, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("energyClass"))
            {
                predicates.add(criteriaBuilder.equal(energyClass, entry.getValue()));
            } 

            if(entry.getKey().equals("rooms"))
            {
                predicates.add(criteriaBuilder.gt(roomsNumber, Integer.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasAirConditioning"))
            {
                predicates.add(criteriaBuilder.equal(airConditioning, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasHeating"))
            {
                predicates.add(criteriaBuilder.equal(heating, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasElevator"))
            {
                predicates.add(criteriaBuilder.equal(elevator, Boolean.valueOf(entry.getValue())));
            }

            if(entry.getKey().equals("hasConcierge"))
            {
                predicates.add(criteriaBuilder.equal(concierge, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasTerrace"))
            {
                predicates.add(criteriaBuilder.equal(terrace, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarage"))
            {
                predicates.add(criteriaBuilder.equal(garage, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasBalcony"))
            {
                predicates.add(criteriaBuilder.equal(balcony, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarden"))
            {
                predicates.add(criteriaBuilder.equal(garden, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasSwimmingPool"))
            {
                predicates.add(criteriaBuilder.equal(swimmingPool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPark"))
            {
                predicates.add(criteriaBuilder.equal(nearPark, Boolean.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("isNearSchool"))
            {
                predicates.add(criteriaBuilder.equal(nearSchool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPublicTransport"))
            {
                predicates.add(criteriaBuilder.equal(nearPublicTransport, Boolean.valueOf(entry.getValue())));
            }  
        }

        return predicates;
    }
}