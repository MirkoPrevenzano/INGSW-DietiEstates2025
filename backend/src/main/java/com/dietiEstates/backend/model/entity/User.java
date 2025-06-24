
package com.dietiEstates.backend.model.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dietiEstates.backend.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



//@MappedSuperclass
@Entity(name = "User")
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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