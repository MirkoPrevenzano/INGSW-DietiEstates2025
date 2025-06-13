
package com.dietiEstates.backend.security;

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
    private final String SECRET_KEY = "w4nw7RJyMobORgdBx4cj80GjLUMBSscPaZ1HOiiQlwo="; // generated with: openssl rand -base64 32
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

    


    public String generateAccessToken(UserDetails userDetails)
    {
        String accessToken = JWT.create()
                                .withSubject(userDetails.getUsername())
                                .withIssuedAt(new Date(System.currentTimeMillis()))
                                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                .sign(algorithm);
        
        return accessToken;
    }


    public boolean verifyToken(String token)
    {
        /* JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token); */
        if(getDecodedJWT(token) != null)
            return true;

        return false;
    }


    public String extractSubject(String token) 
    {
        return getDecodedJWT(token).getSubject();
    }


    public String extractIssuer(String token) 
    {
        return getDecodedJWT(token).getIssuer();
    }


    public String extractIssuingDate(String token) 
    {
        return getDecodedJWT(token).getIssuer();
    }


    public Date extractExpirationDate(String token) 
    {
        return getDecodedJWT(token).getExpiresAt();
    }


    public String[] extractRoles(String token) 
    {
        return getDecodedJWT(token).getClaim("roles").asArray(String.class);
    }




    private DecodedJWT getDecodedJWT(String token)
    {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }
}