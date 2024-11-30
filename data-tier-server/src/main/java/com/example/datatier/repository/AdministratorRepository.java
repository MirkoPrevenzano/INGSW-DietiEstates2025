package com.example.datatier.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.example.datatier.model.Administrator;


public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    
   Administrator findByUsername(String username);
   

    

}
/*
 * JpaRepository fornisce una serie di metodi preimpostati per operazioni CRUD comuni, tra cui:

findById(ID id): Trova un'entità per ID.
findAll(): Trova tutte le entità.
save(S entity): Salva un'entità.
deleteById(ID id): Elimina un'entità per ID.
existsById(ID id): Verifica se un'entità esiste per ID.
 */
