package com.example.datatier.dto;

import lombok.Data;

@Data
public class UserAuthDTO {
    private String username;
    private String password;
    private String role;
}
