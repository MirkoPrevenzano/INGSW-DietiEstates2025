
package com.dietiEstates.backend.util;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;

import lombok.experimental.UtilityClass;

// TODO: creare JWT exc
@UtilityClass
public class JwtUtil 
{
    private final String ISSUER = "dieti-estates";
    private final String SECRET_KEY = "w4nw7RJyMobORgdBx4cj80GjLUMBSscPaZ1HOiiQlwo="; // generated with: openssl rand -base64 32 // TODO: nascondere
    private final long EXPIRATION_TIME = 24 * 60 * 60;
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());
    private final JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
    

    public String generateAccessToken(UserDetails userDetails)
    {
        String accessToken = JWT.create()
                                .withIssuer(ISSUER)
                                .withIssuedAt(new Date(System.currentTimeMillis()))
                                .withSubject(userDetails.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                .sign(ALGORITHM);
        
        return accessToken;
    }

    
    public VerifiedJwt verifyToken(String token)
    {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return new VerifiedJwt(decodedJWT);
    }




    public class VerifiedJwt 
    {
        private final DecodedJWT decodedJWT;


        private VerifiedJwt(DecodedJWT decodedJWT) 
        {
            this.decodedJWT = decodedJWT;
        }


        public String getSubject() 
        {
            return decodedJWT.getSubject();
        }

        public String getIssuer() 
        {
            return decodedJWT.getIssuer();
        }

        public Date getIssuingDate() 
        {
            return decodedJWT.getIssuedAt();
        }

        public Date getExpirationDate() 
        {
            return decodedJWT.getExpiresAt();
        }

        public String[] getRoles() 
        {
            return decodedJWT.getClaim("roles").asArray(String.class);
        }
    }
}