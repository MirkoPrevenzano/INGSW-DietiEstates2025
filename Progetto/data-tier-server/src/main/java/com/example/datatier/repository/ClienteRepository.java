package com.example.datatier.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datatier.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{
    Cliente findByEmail(String email);
    
}
