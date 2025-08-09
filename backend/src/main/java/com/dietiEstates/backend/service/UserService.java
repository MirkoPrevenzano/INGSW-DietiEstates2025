
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.UpdatePasswordDTO;
import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.User;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.resolver.UserLoadingStrategyResolver;
import com.dietiEstates.backend.strategy.UserLoadingStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService
{
    private final UserLoadingStrategyResolver userLoadingStrategyResolver;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        int index = username.indexOf("/");

        Role role;
        try 
        {
            role = Role.of(username.substring(index + 1)); 
        } 
        catch (IllegalArgumentException e) 
        {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }

        username = username.substring(0, index);

        UserLoadingStrategy userLoadingStrategy = userLoadingStrategyResolver.getUserLoadingStrategy(role);
        UserDetails user = userLoadingStrategy.loadUser(username);

        return user;
    }

    @Transactional
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) throws UsernameNotFoundException, 
                                                                                            IllegalArgumentException, MappingException
    {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException(""));

        if(!(passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())))
        {
            log.error("The \"old password\" you have inserted do not correspond to your current password");
            throw new IllegalArgumentException("The \"old password\" you have inserted do not correspond to your current password");
        }

        if((passwordEncoder.matches(updatePasswordDTO.getNewPassword(), user.getPassword())))
        {
            log.error("The \"new password\" you have inserted can't be equal to your current password");
            throw new IllegalArgumentException("\"The \"new password\" you have inserted can't be equal to your current password");
        }
        
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);

        log.info("Password was updated successfully!");
    }
}