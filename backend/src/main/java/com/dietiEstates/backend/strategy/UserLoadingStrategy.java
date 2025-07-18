
package com.dietiEstates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dietiEstates.backend.resolver.Supportable;


public interface UserLoadingStrategy extends Supportable<String>
{
    public UserDetails loadUser(String username) throws UsernameNotFoundException;
}