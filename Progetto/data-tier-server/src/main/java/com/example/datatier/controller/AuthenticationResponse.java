package com.example.datatier.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token)
    {
        this.token=token;
    }

}
