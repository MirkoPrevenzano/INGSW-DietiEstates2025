
package com.dietiestates.backend.repository.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiestates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.extra.CoordinatesBoundingBox;
import com.dietiestates.backend.factory.RealEstateRootFactory;
import com.dietiestates.backend.model.entity.Address;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.resolver.RealEstateRootFactoryResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class RealEstateCriteriaRepositoryImpl implements RealEstateCriteriaRepository 
{
    @PersistenceContext
    private EntityManager entityManager;

    private final RealEstateRootFactoryResolver realEstateRootFactoryResolver;

    private static final String AGENT = "agent";

    private static final String REAL_ESTATE_ID = "realEstateId";

    private static final String TITLE = "title";

    private static final String DESCRIPTION = "description";

    private static final String UPLOADING_DATE = "uploadingDate";

    private static final String USER_ID = "userId";

    private static final String REAL_ESTATE_STATS = "realEstateStats";

    private static final String PRICE = "price";

    private static final String ADDRESS = "address";

    private static final String STREET = "street";

    private static final String LATITUDE = "latitude";

    private static final String LONGITUDE = "longitude";

    private static final String INTERNAL_FEATURES = "internalFeatures";

    private static final String EXTERNAL_FEATURES = "externalFeatures";

    private static final String ENERGY_CLASS = "energyClass";

    private static final String MIN_PRICE = "minPrice";

    private static final String MAX_PRICE = "maxPrice";

    private static final String ROOMS_NUMBER = "roomsNumber";

    private static final String AIR_CONDITIONING = "airConditioning";

    private static final String HEATING = "heating";
    
    private static final String ELEVATOR = "elevator";

    private static final String CONCIERGE = "concierge";

    private static final String TERRACE = "terrace";

    private static final String GARAGE = "garage";

    private static final String BALCONY = "balcony";

    private static final String GARDEN = "garden";

    private static final String SWIMMING_POOL = "swimmingPool";

    private static final String NEAR_PARK = "nearPark";

    private static final String NEAR_SCHOOL = "nearSchool";

    private static final String NEAR_PUBLIC_TRANSPORT = "nearPublicTransport";



    @Override
    public Page<RealEstatePreviewInfoDto> findRealEstatePreviewInfosByFilters(Map<String,String> filters, Pageable page, CoordinatesBoundingBox coordinatesBoundingBox) 
    {        
        CriteriaQuery<RealEstatePreviewInfoDto> query = getPreviewsQueryByFilters(filters, coordinatesBoundingBox);
        List<RealEstatePreviewInfoDto> pageList = entityManager.createQuery(query)
                                                               .setFirstResult((int)page.getOffset())
                                                               .setMaxResults(page.getPageSize())
                                                               .getResultList(); 

        CriteriaQuery<Long> countQuery = getPreviewsCountQueryByFilters(filters, coordinatesBoundingBox);
        long totalElements = entityManager.createQuery(countQuery)
                                          .getSingleResult();       
        
        return new PageImpl<>(pageList, page, totalElements);
    }


    @Override
    public List<AgentRecentRealEstateDto> findAgentRecentRealEstatesByAgentId(Long agentId, Integer limit) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AgentRecentRealEstateDto> query = criteriaBuilder.createQuery(AgentRecentRealEstateDto.class);

        Root<RealEstate> realEstate = query.from(RealEstate.class);
        Join<RealEstate, Agent> agentJoin = realEstate.join(AGENT, JoinType.INNER);

        query.select(criteriaBuilder.construct(AgentRecentRealEstateDto.class, realEstate.get(REAL_ESTATE_ID), 
                                                                                           realEstate.get(TITLE), 
                                                                                           realEstate.get(DESCRIPTION), 
                                                                                           realEstate.get(UPLOADING_DATE)))
                                    .where(criteriaBuilder.equal(agentJoin.get(USER_ID), agentId))
                                    .orderBy(criteriaBuilder.desc(realEstate.get(UPLOADING_DATE)));

        return entityManager.createQuery(query)
                            .setMaxResults(limit)
                            .getResultList();
    }


    @Override
    public List<AgentDashboardRealEstateStatsDto> findAgentDashboardRealEstateStatsByAgentId(Long agentId, Pageable page) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AgentDashboardRealEstateStatsDto> query = criteriaBuilder.createQuery(AgentDashboardRealEstateStatsDto.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
        Join<RealEstate, Agent> agentJoin = realEstate.join(AGENT, JoinType.INNER);

        query.select(criteriaBuilder.construct(AgentDashboardRealEstateStatsDto.class, realEstate.get(REAL_ESTATE_ID), 
                                                                                                   realEstate.get(TITLE), 
                                                                                                   realEstate.get(UPLOADING_DATE), 
                                                                                                   realEstate.get(REAL_ESTATE_STATS).get("viewsNumber"),
                                                                                                   realEstate.get(REAL_ESTATE_STATS).get("visitsNumber"),
                                                                                                   realEstate.get(REAL_ESTATE_STATS).get("offersNumber")))
                                    .where(criteriaBuilder.equal(agentJoin.get(USER_ID), agentId))
                                    .orderBy(criteriaBuilder.asc(realEstate.get(REAL_ESTATE_ID)));


        if(page != null)
        {
            return entityManager.createQuery(query)
                                .setFirstResult((int)page.getOffset())
                                .setMaxResults(page.getPageSize())
                                .getResultList();
        }
        else
        {
           return entityManager.createQuery(query)
                               .getResultList(); 
        }
    }       
    

    @Override
    public Long findLastUploadedByAgentId(Long agentId) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
        Join<RealEstate, Agent> agentJoin = realEstate.join(AGENT, JoinType.INNER);

        query.select(realEstate.get(REAL_ESTATE_ID))
             .where(criteriaBuilder.equal(agentJoin.get(USER_ID), agentId))
             .orderBy(criteriaBuilder.desc(realEstate.get(REAL_ESTATE_ID)));

        return entityManager.createQuery(query)
                            .setMaxResults(1)
                            .getSingleResult();
    }   


    
    private CriteriaQuery<RealEstatePreviewInfoDto> getPreviewsQueryByFilters(Map<String,String> filters, CoordinatesBoundingBox coordinatesBoundingBox)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RealEstatePreviewInfoDto> query = criteriaBuilder.createQuery(RealEstatePreviewInfoDto.class);
        
        Root<RealEstate> realEstate = query.from(RealEstate.class);
        Join<RealEstate, Address> addressJoin = realEstate.join(ADDRESS, JoinType.INNER);

        ContractType contractType = ContractType.fromValue(filters.get("type"));
        RealEstateRootFactory<? extends RealEstate> realEstateRootFactory = realEstateRootFactoryResolver.getFactory(contractType);
        Root<? extends RealEstate> realEstateType = realEstateRootFactory.create(query);

        List<Predicate> predicates = getPredicates(criteriaBuilder, filters, coordinatesBoundingBox, realEstate, addressJoin, realEstateType); 

        query.select(criteriaBuilder.construct(RealEstatePreviewInfoDto.class, realEstate.get(REAL_ESTATE_ID),
                                                                                           realEstate.get(TITLE),
                                                                                           realEstate.get(DESCRIPTION),
                                                                                           realEstate.get(PRICE),
                                                                                           addressJoin.get(STREET),
                                                                                           addressJoin.get(LONGITUDE),
                                                                                           addressJoin.get(LATITUDE)))
                                    .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));    
        
        return query;
    }


    private CriteriaQuery<Long> getPreviewsCountQueryByFilters(Map<String,String> filters, CoordinatesBoundingBox coordinatesBoundingBox) 
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            
        Root<RealEstate> realEstate = countQuery.from(RealEstate.class);
        Join<RealEstate, Address> addressJoin = realEstate.join(ADDRESS, JoinType.INNER);

        ContractType contractType = ContractType.fromValue(filters.get("type"));
        RealEstateRootFactory<? extends RealEstate> realEstateRootFactory = realEstateRootFactoryResolver.getFactory(contractType);
        Root<? extends RealEstate> realEstateType = realEstateRootFactory.create(countQuery);

        List<Predicate> predicates = getPredicates(criteriaBuilder, filters, coordinatesBoundingBox, realEstate, addressJoin, realEstateType);

        countQuery.select(criteriaBuilder.count(realEstate))
                  .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return countQuery;
    }


    private List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Map<String, String> filters, CoordinatesBoundingBox coordinatesBoundingBox, 
                                          Root<RealEstate> realEstate, Join<RealEstate,Address> addressJoin, Root<? extends RealEstate> realEstateType) 
    {
        List<Predicate> predicates = new ArrayList<>();

        addRealEstateSubtypePredicate(predicates, criteriaBuilder, realEstate, realEstateType);

        addBoundingBoxPredicates(predicates, criteriaBuilder, coordinatesBoundingBox, addressJoin);

        addGeneralPredicates(predicates, criteriaBuilder, filters, realEstate);

        addInternalFeaturesPredicates(predicates, criteriaBuilder, filters, realEstate);

        addExternalFeaturesPredicates(predicates, criteriaBuilder, filters, realEstate);

        addNearByPredicates(predicates, criteriaBuilder, filters, realEstate);

        return predicates;
    }


    private void addRealEstateSubtypePredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                               Root<RealEstate> realEstate, Root<? extends RealEstate> realEstateType)
    {
        Path<Long> realEstateId = realEstate.get(REAL_ESTATE_ID);
        Path<Long> realEstateTypeId = realEstateType.get(REAL_ESTATE_ID);
            
        predicates.add(criteriaBuilder.equal(realEstateId, realEstateTypeId));
    }


    private void addBoundingBoxPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                          CoordinatesBoundingBox coordinatesBoundingBox, Join<RealEstate,Address> addressJoin)
    {
        Path<Double> latitude = addressJoin.get(LATITUDE);
        Path<Double> longitude = addressJoin.get(LONGITUDE);   

        predicates.add(criteriaBuilder.ge(latitude, coordinatesBoundingBox.getMinLatitude()));
        predicates.add(criteriaBuilder.le(latitude, coordinatesBoundingBox.getMaxLatitude()));
        predicates.add(criteriaBuilder.ge(longitude, coordinatesBoundingBox.getMinLongitude()));
        predicates.add(criteriaBuilder.le(longitude, coordinatesBoundingBox.getMaxLongitude()));  
    }


    private void addGeneralPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                      Map<String, String> filters, Root<RealEstate> realEstate)
    {
        Path<Double> price = realEstate.get(PRICE);         
        Path<String> energyClass = realEstate.get(ENERGY_CLASS);

        if (filters.containsKey(MIN_PRICE)) 
            predicates.add(criteriaBuilder.ge(price, Double.valueOf(filters.get(MIN_PRICE))));
        
        if (filters.containsKey(MAX_PRICE)) 
            predicates.add(criteriaBuilder.le(price, Double.valueOf(filters.get(MAX_PRICE))));
        
        if (filters.containsKey(ENERGY_CLASS)) 
            predicates.add(criteriaBuilder.equal(energyClass, filters.get(ENERGY_CLASS)));
    }


    private void addInternalFeaturesPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                               Map<String, String> filters, Root<RealEstate> realEstate)
    {

        Path<Integer> roomsNumber = realEstate.get(INTERNAL_FEATURES).get(ROOMS_NUMBER);
        Path<Boolean> airConditioning = realEstate.get(INTERNAL_FEATURES).get(AIR_CONDITIONING);
        Path<Boolean> heating = realEstate.get(INTERNAL_FEATURES).get(HEATING);

        if (filters.containsKey(ROOMS_NUMBER)) 
            predicates.add(criteriaBuilder.ge(roomsNumber, Integer.valueOf(filters.get(ROOMS_NUMBER))));
        
        if (filters.containsKey(AIR_CONDITIONING)) 
            predicates.add(criteriaBuilder.equal(airConditioning, Boolean.valueOf(filters.get(AIR_CONDITIONING))));
        
        if (filters.containsKey(HEATING)) 
            predicates.add(criteriaBuilder.equal(heating, Boolean.valueOf(filters.get(HEATING))));
        
    }


    private void addExternalFeaturesPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                               Map<String, String> filters, Root<RealEstate> realEstate)
    {
        Path<Boolean> elevator = realEstate.get(EXTERNAL_FEATURES).get(ELEVATOR);
        Path<Boolean> concierge = realEstate.get(EXTERNAL_FEATURES).get(CONCIERGE);
        Path<Boolean> terrace = realEstate.get(EXTERNAL_FEATURES).get(TERRACE);
        Path<Boolean> garage = realEstate.get(EXTERNAL_FEATURES).get(GARAGE);
        Path<Boolean> balcony = realEstate.get(EXTERNAL_FEATURES).get(BALCONY);
        Path<Boolean> garden = realEstate.get(EXTERNAL_FEATURES).get(GARDEN);
        Path<Boolean> swimmingPool = realEstate.get(EXTERNAL_FEATURES).get(SWIMMING_POOL);

        if (filters.containsKey(ELEVATOR)) 
            predicates.add(criteriaBuilder.equal(elevator, Boolean.valueOf(filters.get(ELEVATOR))));

        if (filters.containsKey(CONCIERGE)) 
            predicates.add(criteriaBuilder.equal(concierge, Boolean.valueOf(filters.get(CONCIERGE))));

        if (filters.containsKey(TERRACE)) 
            predicates.add(criteriaBuilder.equal(terrace, Boolean.valueOf(filters.get(TERRACE))));

        if (filters.containsKey(GARAGE)) 
            predicates.add(criteriaBuilder.equal(garage, Boolean.valueOf(filters.get(GARAGE))));

        if (filters.containsKey(BALCONY))
            predicates.add(criteriaBuilder.equal(balcony, Boolean.valueOf(filters.get(BALCONY))));

        if (filters.containsKey(GARDEN)) 
            predicates.add(criteriaBuilder.equal(garden, Boolean.valueOf(filters.get(GARDEN))));

        if (filters.containsKey(SWIMMING_POOL)) 
            predicates.add(criteriaBuilder.equal(swimmingPool, Boolean.valueOf(filters.get(SWIMMING_POOL))));
    }


    private void addNearByPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, 
                                     Map<String, String> filters, Root<RealEstate> realEstate)
    {
        Path<Boolean> nearPark = realEstate.get(EXTERNAL_FEATURES).get(NEAR_PARK);
        Path<Boolean> nearSchool = realEstate.get(EXTERNAL_FEATURES).get(NEAR_SCHOOL);
        Path<Boolean> nearPublicTransport = realEstate.get(EXTERNAL_FEATURES).get(NEAR_PUBLIC_TRANSPORT);

        if (filters.containsKey("isNearPark")) 
            predicates.add(criteriaBuilder.equal(nearPark, Boolean.valueOf(filters.get("isNearPark"))));

        if (filters.containsKey("isNearSchool")) 
            predicates.add(criteriaBuilder.equal(nearSchool, Boolean.valueOf(filters.get("isNearSchool"))));

        if (filters.containsKey("isNearPublicTransport")) 
            predicates.add(criteriaBuilder.equal(nearPublicTransport, Boolean.valueOf(filters.get("isNearPublicTransport"))));
    
    }
}