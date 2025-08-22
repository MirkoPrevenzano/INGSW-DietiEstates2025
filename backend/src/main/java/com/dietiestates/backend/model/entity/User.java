
package com.dietiestates.backend.model.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dietiestates.backend.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "User")
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull
    @Column(nullable = false, 
	        updatable = true)
    private String name;

    @NotNull
    @Column(nullable = false, 
	        updatable = true)
    private String surname;

    @NotNull
    @Column(nullable = false, 
	        updatable = true)
    private String username;

    @NotNull
    @Column(nullable = false, 
	        updatable = true)
    private String password; 

    @Transient
    Role role;
    

    protected User(String name, String surname, String username, String password) 
    {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }


    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() 
    {
	    return List.of(new SimpleGrantedAuthority(role.name()));
    }
}