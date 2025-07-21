
package com.dietiEstates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.resolver.Supportable;


public interface UserLoadingStrategy extends Supportable<Role>
{
    public UserDetails loadUser(String username) throws UsernameNotFoundException;
}