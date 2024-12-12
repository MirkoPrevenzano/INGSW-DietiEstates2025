package com.amigoscode.spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amigoscode.spring_data_jpa.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book,Long>
{
}
