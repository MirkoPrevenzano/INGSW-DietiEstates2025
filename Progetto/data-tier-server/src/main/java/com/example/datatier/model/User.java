package com.example.datatier.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails{

    public Long getId();
    public String getUsername();
    public String getPassword();
}
