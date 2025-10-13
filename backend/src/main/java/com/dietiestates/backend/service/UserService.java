
package com.dietiestates.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.request.UpdatePasswordDto;
import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.model.entity.User;
import com.dietiestates.backend.repository.UserRepository;
import com.dietiestates.backend.resolver.UserLoadingStrategyResolver;
import com.dietiestates.backend.strategy.UserLoadingStrategy;

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
            role = Role.fromValue(username.substring(index + 1)); 
        } 
        catch (IllegalArgumentException e) 
        {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }

        username = username.substring(0, index);

        UserLoadingStrategy userLoadingStrategy = userLoadingStrategyResolver.getStrategy(role);

        UserDetails userDetails = userLoadingStrategy.loadUser(username);
        userLoadingStrategy.setRole(userDetails);

        return userDetails;
    }


    @Transactional
    public void updatePassword(String username, UpdatePasswordDto updatePasswordDto)
    {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("Utente '" + username + "' non trovato nel DB!"));

        if(!(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())))
        {
            log.error("The \"old password\" you have inserted do not correspond to your current password!");
            throw new IllegalArgumentException("The \"old password\" you have inserted do not correspond to your current password!");
        }

        if((passwordEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword())))
        {
            log.error("The \"new password\" you have inserted can't be equal to your current password!");
            throw new IllegalArgumentException("\"The \"new password\" you have inserted can't be equal to your current password!");
        }
        
        String encodedPassword = passwordEncoder.encode(updatePasswordDto.getNewPassword());
        user.setPassword(encodedPassword);

        log.info("Password was updated successfully!");
    }
}