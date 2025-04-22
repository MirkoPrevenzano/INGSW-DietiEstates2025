
package com.dietiEstates.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Entity(name = "Photo")
@Table(name = "photo")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Photo 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @NonNull
    @Column(name = "amazon_s3_key",
            nullable = false, 
            updatable = true) 
    private String amazonS3Key;
}