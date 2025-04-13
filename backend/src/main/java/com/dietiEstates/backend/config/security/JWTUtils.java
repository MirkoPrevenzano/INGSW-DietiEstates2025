
package com.dietiEstates.backend.config.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.experimental.UtilityClass;

import com.auth0.jwt.JWTVerifier;



@UtilityClass
public class JWTUtils 
{
    private static final String SECRET_KEY = "w4nw7RJyMobORgdBx4cj80GjLUMBSscPaZ1HOiiQlwo="; // generated with: openssl rand -base64 32
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

    

    public String generateAccessToken(UserDetails userDetails)
    {
        String accessToken = JWT.create()
                                .withSubject(userDetails.getUsername())
                                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                .withIssuedAt(new Date(System.currentTimeMillis()))
                                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                                .sign(algorithm);
        
        return accessToken;
    }


    public DecodedJWT verifyToken(String token)
    {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }
}