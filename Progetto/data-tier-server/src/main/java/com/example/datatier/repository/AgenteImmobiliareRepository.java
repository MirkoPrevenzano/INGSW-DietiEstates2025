package com.example.datatier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datatier.model.AgenteImmobiliare;

public interface AgenteImmobiliareRepository extends JpaRepository<AgenteImmobiliare, Long> {
    AgenteImmobiliare findByUsername(String username);

}
