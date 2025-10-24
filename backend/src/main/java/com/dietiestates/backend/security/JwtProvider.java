
package com.dietiestates.backend.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;


@Component
public class JwtProvider 
{
    private static final String ISSUER = "Dieti Estates";

    private static final long EXPIRATION_TIME = 1L * 24 * 60 * 60 * 1000;
    
    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;



    public JwtProvider(@Value("${dietiestates.jwt.secret}") String secretKey) 
    {
        this.algorithm = Algorithm.HMAC256(secretKey.getBytes());
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    

    public String generateAccessToken(UserDetails userDetails)
    {
        return   JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm);
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