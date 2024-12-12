package com.amigoscode.spring_data_jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.amigoscode.spring_data_jpa.model.StudentIdCard;


@Repository
public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> 
{
}