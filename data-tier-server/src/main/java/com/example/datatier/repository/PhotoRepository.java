package com.example.datatier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datatier.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long>{}
