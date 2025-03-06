package com.example.datatier.service;





import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;
import com.example.datatier.model.Administrator;
import com.example.datatier.model.User;
import com.example.datatier.model.repository.AdministratorRepository;
import com.example.datatier.service.auth_service.AuthServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class AdministratorService implements AuthServiceInterface{
     AdministratorRepository administratorRepository;
     PasswordValidatorService passwordValidator;
     PasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, 
                                    PasswordValidatorService passwordValidator,
                                    PasswordEncoder passwordEncoder)
    {
        this.administratorRepository=administratorRepository;
        this.passwordValidator=passwordValidator;
    }

    public Administrator save(Administrator administrator)
    {
        if(passwordValidator.isValid(administrator.getPassword()))
            return administratorRepository.save(administrator);
        throw new IllegalArgumentException("Password invalida");
    }

    public Optional<Administrator> findByUsername(String username)
    {
        return administratorRepository.findByUsername(username);
    }


    

    @Transactional
    public Administrator updatePassword(String password, String username)
    {
        Optional<Administrator> administrator= findByUsername(username);
            
        if(administrator.isPresent()){
            administrator.get().setPassword(password);
            return save(administrator.get());
        }
        else
            throw new IllegalArgumentException("Admin not found");
            
    
    }

    
    @Transactional
    public Administrator createAdministrator(String username, String usernameNewAdministrator) {
        Optional<Administrator> administrator= findByUsername(username);
        
        Optional<Administrator> administratorNew=findByUsername(usernameNewAdministrator);
            
        if (administratorNew.isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }
        if (administrator.get().getResponsible() == null) {
            Administrator administratorGenerate= generateAdministrator(usernameNewAdministrator, administrator.get());
            return save(administratorGenerate);
        }
        throw new IllegalArgumentException("Administrator is not authorized to create new administrators");
          
       
    }


    private Administrator generateAdministrator(String username,Administrator administrator) {
        Administrator newAdministrator = new Administrator();
        newAdministrator.setUsername(username);
        newAdministrator.setAgencyName(administrator.getAgencyName());
        String passwordDefault="Password123.";
        newAdministrator.setPassword(passwordDefault);
        newAdministrator.setResponsible(administrator);
        administrator.addManager(newAdministrator);
        
        return newAdministrator;
    }

    @Transactional
    public String getAgencyByUsername(String username)
    {
        Optional<Administrator> admin= findByUsername(username);
        
        if(admin.isPresent())
            return admin.get().getAgencyName();
        else
            throw new IllegalArgumentException("Admin not found");
    }

    @Override
    public User authenticate(UserAuthDTO userAuthDTO) {
       Optional<Administrator> admOptional= findByUsername(userAuthDTO.getUsername());
       if(admOptional.isPresent())
        {
            if (passwordEncoder.matches(userAuthDTO.getPassword(), admOptional.get().getPassword()))
                return admOptional.get();
            else
                throw new IllegalArgumentException("Password wrong");    
        }
        else
            throw new IllegalArgumentException("Admin not found");
        
    }
    
    @Override
    public User registrate(UserDTO userDTO) {
        return null;
    }


}
