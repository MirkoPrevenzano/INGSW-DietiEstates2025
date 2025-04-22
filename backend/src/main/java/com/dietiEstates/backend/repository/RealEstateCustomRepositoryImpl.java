
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

        /* 
            Root<?> r
            String realEstateType = filters.get("type");
            if(forSale)
                r = criteriaQuery.from(RealEstateForSale.class);
            else
                r = criteriaQuery.from(RealEstateForRent.class);                

        */


        Root<?> realEstateForSale;
        Root<?> realEstateForRent;

        String realEstateType = filters.get("type");
        //realEstate = factory.getTypeQuery(realEstateType);
        if(realEstateType != null && realEstateType.equals("For Sale"))
        {
            realEstateForSale = criteriaQuery.from(RealEstateForSale.class);
            predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), realEstateForSale.get("realEstateId")));
            predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), address.get("addressId")));
            
            criteriaQuery.select(criteriaBuilder.construct(RealEstatePreviewDTO.class, realEstate.get("realEstateId"),
                                                                              realEstate.get("title"),
                                                                              realEstate.get("description"),
                                                                              realEstate.get("price"),
                                                                              address.get("street"),
                                                                              address.get("longitude"),
                                                                              address.get("latitude")))
                 .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        else if(realEstateType != null && realEstateType.equals("For Rent"))
        {
            realEstateForRent = criteriaQuery.from(RealEstateForRent.class);
            predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), realEstateForRent.get("realEstateId")));
            predicates.add(criteriaBuilder.equal(realEstate.get("realEstateId"), address.get("addressId")));
            criteriaQuery.select(criteriaBuilder.construct(RealEstatePreviewDTO.class, realEstate.get("realEstateId"),
                                                                              realEstate.get("title"),
                                                                              realEstate.get("description"),
                                                                              realEstate.get("price"),
                                                                              address.get("street"),
                                                                              address.get("longitude"),
                                                                              address.get("latitude")))                 
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }




        CriteriaBuilder criteriaBuilder2 = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaCountQuery = criteriaBuilder2.createQuery(Long.class);
        
        Root<RealEstate> realEstate2 = criteriaCountQuery.from(RealEstate.class);
        Root<Address> address2 = criteriaCountQuery.from(Address.class);
    
        Path<String> energyClass2 = realEstate2.get("energyClass");
        Path<Double> price2 = realEstate2.get("price");
        //VEDERE COME FARE con city
        Path<String> city2 = address2.get("city");
        Path<Double> latitude2 = address2.get("latitude");
        Path<Double> longitude2 = address2.get("longitude");  
        Path<Integer> roomsNumber2 = realEstate2.get("internalFeatures").get("roomsNumber");
        Path<Boolean> airConditioning2 = realEstate2.get("internalFeatures").get("airConditioning");
        Path<Boolean> heating2 = realEstate2.get("internalFeatures").get("heating");
        Path<Boolean> elevator2 = realEstate2.get("externalFeatures").get("elevator");
        Path<Boolean> concierge2 = realEstate2.get("externalFeatures").get("concierge");
        Path<Boolean> terrace2 = realEstate2.get("externalFeatures").get("terrace");
        Path<Boolean> garage2 = realEstate2.get("externalFeatures").get("garage");
        Path<Boolean> balcony2 = realEstate2.get("externalFeatures").get("balcony");
        Path<Boolean> garden2 = realEstate2.get("externalFeatures").get("garden");
        Path<Boolean> swimmingPool2 = realEstate2.get("externalFeatures").get("swimmingPool");
        Path<Boolean> nearPark2 = realEstate2.get("externalFeatures").get("nearPark");
        Path<Boolean> nearSchool2 = realEstate2.get("externalFeatures").get("nearSchool");
        Path<Boolean> nearPublicTransport2 = realEstate2.get("externalFeatures").get("nearPublicTransport");

        List<Predicate> predicates2 = new ArrayList<>();

        predicates2.add(criteriaBuilder2.ge(latitude2, latLongMinMax.getLatMin()));
        predicates2.add(criteriaBuilder2.le(latitude2, latLongMinMax.getLatMax()));
        predicates2.add(criteriaBuilder2.le(longitude2, latLongMinMax.getLongMin()));
        predicates2.add(criteriaBuilder2.ge(longitude2, latLongMinMax.getLongMax()));

        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            if(entry.getKey().equals("minPrice"))
            {
                predicates2.add(criteriaBuilder2.ge(price2, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("maxPrice"))
            {
                predicates2.add(criteriaBuilder2.le(price2, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("energyClass"))
            {
                predicates2.add(criteriaBuilder2.equal(energyClass2, entry.getValue()));
            } 

            if(entry.getKey().equals("rooms"))
            {
                predicates2.add(criteriaBuilder2.gt(roomsNumber2, Integer.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasAirConditioning"))
            {
                predicates2.add(criteriaBuilder2.equal(airConditioning2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasHeating"))
            {
                predicates2.add(criteriaBuilder2.equal(heating2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasElevator"))
            {
                predicates2.add(criteriaBuilder2.equal(elevator2, Boolean.valueOf(entry.getValue())));
            }

            if(entry.getKey().equals("hasConcierge"))
            {
                predicates2.add(criteriaBuilder2.equal(concierge2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasTerrace"))
            {
                predicates2.add(criteriaBuilder2.equal(terrace2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarage"))
            {
                predicates2.add(criteriaBuilder2.equal(garage2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasBalcony"))
            {
                predicates2.add(criteriaBuilder2.equal(balcony2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarden"))
            {
                predicates2.add(criteriaBuilder2.equal(garden2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasSwimmingPool"))
            {
                predicates2.add(criteriaBuilder2.equal(swimmingPool2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPark"))
            {
                predicates2.add(criteriaBuilder2.equal(nearPark2, Boolean.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("isNearSchool"))
            {
                predicates2.add(criteriaBuilder2.equal(nearSchool2, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPublicTransport"))
            {
                predicates2.add(criteriaBuilder2.equal(nearPublicTransport2, Boolean.valueOf(entry.getValue())));
            }  
            
            if(entry.getKey().equals("city"))
            {
                predicates2.add(criteriaBuilder2.equal(city2, entry.getValue()));
            } 
        } 


        Root<RealEstateForSale> realEstateForSale2;
        Root<RealEstateForRent> realEstateForRent2;

        String realEstateType2 = filters.get("type");
        //realEstate = factory.getTypeQuery(realEstateType);
        if(realEstateType2 != null && realEstateType2.equals("For Sale"))
        {
            realEstateForSale2 = criteriaCountQuery.from(RealEstateForSale.class);
            predicates2.add(criteriaBuilder.equal(realEstate2.get("realEstateId"), realEstateForSale2.get("realEstateId")));
            predicates2.add(criteriaBuilder.equal(realEstate2.get("realEstateId"), address2.get("addressId")));
            
            criteriaCountQuery.select(criteriaBuilder.count(realEstateForSale2))
                      .where(criteriaBuilder.and(predicates2.toArray(new Predicate[predicates2.size()])));
        }
        else if(realEstateType2 != null && realEstateType2.equals("For Rent"))
        {
            realEstateForRent2 = criteriaCountQuery.from(RealEstateForRent.class);
            predicates2.add(criteriaBuilder.equal(realEstate2.get("realEstateId"), realEstateForRent2.get("realEstateId")));
            predicates2.add(criteriaBuilder.equal(realEstate2.get("realEstateId"), address2.get("addressId")));
            
            criteriaCountQuery.select(criteriaBuilder.count(realEstateForRent2))
                      .where(criteriaBuilder.and(predicates2.toArray(new Predicate[predicates2.size()])));
        }




        Long count = entityManager.createQuery(criteriaCountQuery).getSingleResult();

        List<RealEstatePreviewDTO> list = entityManager.createQuery(criteriaQuery).setFirstResult((int)page.getOffset()).setMaxResults(page.getPageSize())
                            .getResultList(); 

        PageImpl<RealEstatePreviewDTO> pageImpl = new PageImpl<>(list, page, count);
        return pageImpl;
    }


    @Override
    public List<RealEstatePreviewDTO> findRealEstateByFilters4(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax) 
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstatePreviewDTO> criteriaQuery = cb.createQuery(RealEstatePreviewDTO.class);
        
        Root<RealEstate> realEstate = criteriaQuery.from(RealEstate.class);
        Root<Address> address = criteriaQuery.from(Address.class);
    
        Path<String> energyClass = realEstate.get("energyClass");
        Path<Double> price = realEstate.get("price");
        //VEDERE COME FARE con city
        Path<String> city = address.get("city");
        Path<Double> latitude = address.get("latitude");
        Path<Double> longitude = address.get("longitude");
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

        List<Predicate> predicates = new ArrayList<>();
        
        predicates.add(cb.ge(latitude, latLongMinMax.getLatMin()));
        predicates.add(cb.le(latitude, latLongMinMax.getLatMax()));
        predicates.add(cb.le(longitude, latLongMinMax.getLongMin()));
        predicates.add(cb.ge(longitude, latLongMinMax.getLongMax()));

        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            if(entry.getKey().equals("minPrice"))
            {
                predicates.add(cb.ge(price, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("maxPrice"))
            {
                predicates.add(cb.le(price, Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("energyClass"))
            {
                predicates.add(cb.equal(energyClass, entry.getValue()));
            } 

            if(entry.getKey().equals("roomsNumber"))
            {
                predicates.add(cb.gt(roomsNumber, Integer.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasAirConditioning"))
            {
                predicates.add(cb.equal(airConditioning, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasHeating"))
            {
                predicates.add(cb.equal(heating, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasElevator"))
            {
                predicates.add(cb.equal(elevator, Boolean.valueOf(entry.getValue())));
            }

            if(entry.getKey().equals("hasConcierge"))
            {
                predicates.add(cb.equal(concierge, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasTerrace"))
            {
                predicates.add(cb.equal(terrace, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarage"))
            {
                predicates.add(cb.equal(garage, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasBalcony"))
            {
                predicates.add(cb.equal(balcony, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarden"))
            {
                predicates.add(cb.equal(garden, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasSwimmingPool"))
            {
                predicates.add(cb.equal(swimmingPool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPark"))
            {
                predicates.add(cb.equal(nearPark, Boolean.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("isNearSchool"))
            {
                predicates.add(cb.equal(nearSchool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPublicTransport"))
            {
                predicates.add(cb.equal(nearPublicTransport, Boolean.valueOf(entry.getValue())));
            }  
            
            if(entry.getKey().equals("city"))
            {
                predicates.add(cb.equal(city, entry.getValue()));
            } 
        } 

        Root<RealEstateForSale> realEstateForSale;
        Root<RealEstateForRent> realEstateForRent;

        String realEstateType = filters.get("type");
        //realEstate = factory.getTypeQuery(realEstateType);
        if(realEstateType != null && realEstateType.equals("For Sale"))
        {
            realEstateForSale = criteriaQuery.from(RealEstateForSale.class);
            predicates.add(cb.equal(realEstate.get("realEstateId"), realEstateForSale.get("realEstateId")));
            predicates.add(cb.equal(realEstate.get("realEstateId"), address.get("addressId")));
            criteriaQuery.select(cb.construct(RealEstatePreviewDTO.class, realEstate.get("realEstateId"),
                                                                              realEstate.get("title"),
                                                                              realEstate.get("description"),
                                                                              realEstate.get("price"),
                                                                              address.get("street"),
                                                                              address.get("longitude"),
                                                                              address.get("latitude")))
                 .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        else if(realEstateType != null && realEstateType.equals("For Rent"))
        {
            realEstateForRent = criteriaQuery.from(RealEstateForRent.class);
            predicates.add(cb.equal(realEstate.get("realEstateId"), realEstateForRent.get("realEstateId")));
            predicates.add(cb.equal(realEstate.get("realEstateId"), address.get("addressId")));
            criteriaQuery.select(cb.construct(RealEstatePreviewDTO.class, realEstate.get("realEstateId"),
                                                                              realEstate.get("title"),
                                                                              realEstate.get("description"),
                                                                              realEstate.get("price"),
                                                                              address.get("street"),
                                                                              address.get("longitude"),
                                                                              address.get("latitude")))                 
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        List<RealEstatePreviewDTO> list = entityManager.createQuery(criteriaQuery).setFirstResult((int)page.getOffset()).setMaxResults(page.getPageSize())
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




    private void filtersQuery()
    {
        ; /*TODO*/
    }
}