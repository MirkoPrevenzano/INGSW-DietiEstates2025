
package com.dietiEstates.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietiEstates.backend.model.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long> 
{
    public Optional<User> findByUsername(String username);
}