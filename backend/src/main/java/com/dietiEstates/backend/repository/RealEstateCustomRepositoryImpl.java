
package com.dietiEstates.backend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.LatLongMinMax;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import com.dietiEstates.backend.model.Address;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class RealEstateCustomRepositoryImpl implements RealEstateCustomRepository 
{
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public Page<RealEstatePreviewDTO> findRealEstateByFilters3(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax) 
    {
        CriteriaQuery<RealEstatePreviewDTO> criteriaQuery = filtersQuery(filters, latLongMinMax);

        List<RealEstatePreviewDTO> pageList = entityManager.createQuery(criteriaQuery)
                                                           .setFirstResult((int)page.getOffset())
                                                           .setMaxResults(page.getPageSize())
                                                           .getResultList(); 

        List<RealEstatePreviewDTO> totalList = entityManager.createQuery(criteriaQuery)
                                                            .getResultList(); 
        
        PageImpl<RealEstatePreviewDTO> pageImpl = new PageImpl<>(pageList, page, totalList.size());

        return pageImpl;
    }


    @Override
    public List<RealEstatePreviewDTO> findRealEstateByFilters4(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax) 
    {
        CriteriaQuery<RealEstatePreviewDTO> criteriaQuery = filtersQuery(filters, latLongMinMax);

        List<RealEstatePreviewDTO> list = entityManager.createQuery(criteriaQuery)
                                                       .setFirstResult((int)page.getOffset())
                                                       .setMaxResults(page.getPageSize())
                                                       .getResultList(); 
        return list;
    }


    @Override
    public List<RealEstateRecentDTO> findRecentRealEstates(Long agentId, Integer limit) 
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstateRecentDTO> query = cb.createQuery(RealEstateRecentDTO.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
    
        query.select(cb.construct(RealEstateRecentDTO.class, realEstate.get("realEstateId"), realEstate.get("title"), realEstate.get("description"), realEstate.get("uploadingDate")))
             .where(cb.equal(realEstate.get("realEstateAgent").get("userId"), agentId))
             .orderBy(cb.desc(realEstate.get("uploadingDate")));

        return entityManager.createQuery(query)
                            .setMaxResults(limit)
                            .getResultList();
    }


    @Override
    public List<RealEstateStatsDTO> findRealEstateStats2(Long agentId, Pageable page) 
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstateStatsDTO> query = cb.createQuery(RealEstateStatsDTO.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
    
        query.select(cb.construct(RealEstateStatsDTO.class, realEstate.get("realEstateId"), 
                                                                         realEstate.get("title"), 
                                                                         realEstate.get("uploadingDate"), 
                                                                         realEstate.get("realEstateStats").get("viewsNumber"),
                                                                         realEstate.get("realEstateStats").get("visitsNumber"),
                                                                         realEstate.get("realEstateStats").get("offersNumber")))
             .where(cb.equal(realEstate.get("realEstateAgent").get("userId"), agentId))
             .orderBy(cb.asc(realEstate.get("realEstateId")));

        List<RealEstateStatsDTO> list = entityManager.createQuery(query)
                                                     .setFirstResult((int)page.getOffset())
                                                     .setMaxResults(page.getPageSize())
                                                     .getResultList();

        return list;
    }       
    

    @Override
    public Long findLastRealEstate(Long agentId) 
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
    
        query.select(realEstate.get("realEstateId"))
        .where(cb.equal(realEstate.get("realEstateAgent").get("userId"), agentId))
        .orderBy(cb.desc(realEstate.get("realEstateId")));

        Long id = entityManager.createQuery(query)
                               .setMaxResults(1)
                               .getSingleResult();

        return id;
    }   




    @SuppressWarnings("null")
    private CriteriaQuery<RealEstatePreviewDTO> filtersQuery(Map<String,String> filters, LatLongMinMax latLongMinMax)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstatePreviewDTO> criteriaQuery = criteriaBuilder.createQuery(RealEstatePreviewDTO.class);
        

        Root<RealEstate> realEstate = criteriaQuery.from(RealEstate.class);
        Root<Address> address = criteriaQuery.from(Address.class);
    

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
        Path<Double> latitude = address.get("latitude");
        Path<Double> longitude = address.get("longitude");   


        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.ge(latitude, latLongMinMax.getLatMin()));
        predicates.add(criteriaBuilder.le(latitude, latLongMinMax.getLatMax()));
        predicates.add(criteriaBuilder.le(longitude, latLongMinMax.getLongMin()));
        predicates.add(criteriaBuilder.ge(longitude, latLongMinMax.getLongMax()));
              
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


        Root<?> realEstateType = null;

        String type = filters.get("type");
        if(type.equals("For Sale"))
            realEstateType = criteriaQuery.from(RealEstateForSale.class);
        else if(type.equals("For Rent"))
            realEstateType = criteriaQuery.from(RealEstateForRent.class);
        else
            ; // throw Exception;

        predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), realEstateType.get("realEstateId")));
        predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), address.get("addressId")));
            
        criteriaQuery.select(criteriaBuilder.construct(RealEstatePreviewDTO.class, realEstate.get("realEstateId"),
                                                                                               realEstate.get("title"),
                                                                                               realEstate.get("description"),
                                                                                               realEstate.get("price"),
                                                                                               address.get("street"),
                                                                                               address.get("longitude"),
                                                                                               address.get("latitude")))
                     .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));    
        
        return criteriaQuery;
    }
}