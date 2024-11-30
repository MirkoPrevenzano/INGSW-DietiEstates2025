package com.example.datatier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.datatier.model.PropertyAgent;

public interface PropertyAgentRepository extends JpaRepository<PropertyAgent, Long> {
    Optional<PropertyAgent> findByUsername(String username);

}
