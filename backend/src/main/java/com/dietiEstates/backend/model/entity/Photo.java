
package com.dietiEstates.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Entity(name = "Photo")
@Table(name = "photo")
@Data
@NoArgsConstructor
public class Photo 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @NotNull
    @Column(name = "key",
            nullable = false, 
            updatable = true) 
    private String key;


    public Photo(String key) {
        this.key = key;
    }

    
}