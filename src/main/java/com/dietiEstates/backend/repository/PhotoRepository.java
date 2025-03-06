
package com.dietiEstates.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.Photo;


@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long>
{
}