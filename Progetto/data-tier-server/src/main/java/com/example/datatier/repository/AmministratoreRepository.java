package com.example.datatier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.datatier.model.Amministratore;

import jakarta.transaction.Transactional;

public interface AmministratoreRepository extends JpaRepository<Amministratore, Long> {
    
    Amministratore findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Amministratore a SET a.password = :password WHERE a.username = :username")
    boolean updatePassword(String password, String username);
    //per aggiornamenti ed eliminazione personalizzate si necessita specificarel la query

}
/*
 * JpaRepository fornisce una serie di metodi preimpostati per operazioni CRUD comuni, tra cui:

findById(ID id): Trova un'entità per ID.
findAll(): Trova tutte le entità.
save(S entity): Salva un'entità.
deleteById(ID id): Elimina un'entità per ID.
existsById(ID id): Verifica se un'entità esiste per ID.
 */
