package com.example.datatier.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.datatier.model.PropertySell;

public interface PropertySellRepository extends JpaRepository<PropertySell,Long>,JpaSpecificationExecutor<PropertySell>{

}
