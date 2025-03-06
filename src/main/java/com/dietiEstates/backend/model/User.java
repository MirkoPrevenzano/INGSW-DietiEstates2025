
package com.dietiEstates.backend.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String name;

    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String surname;

    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String username;

    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String password; 

    @Enumerated(EnumType.STRING)
    @Transient
    Role role;



    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() 
    {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
