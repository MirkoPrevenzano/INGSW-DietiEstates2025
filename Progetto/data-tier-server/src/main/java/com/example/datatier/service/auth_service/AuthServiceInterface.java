package com.example.datatier.service.auth_service;


import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;
import com.example.datatier.model.User;

public interface AuthServiceInterface {
    User authenticate(UserAuthDTO userAuthDTO);
    User registrate(UserDTO userDTO);
}
