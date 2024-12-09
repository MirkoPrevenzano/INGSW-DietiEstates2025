package com.example.datatier.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY="token";
    public String extractUsername(String token) {
       return exctractClaim(token, Claims::getSubject);
       //uso extracClaim e richiesto getSubject 
    }


    //data una certa funzione resolver, estrae quei campi selezionati
    public <T> T exctractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Metodo che estrai le claims dal token JWT, che sono tutte delle info incorporate come 
     * il nome utente, data di scadenza ecc. 
     * parserBuilder crea un parser JWT, imposto la chiave di firma, analizza poi il token e restituisce le claims
     */
    private Claims extractAllClaims(String token) 
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
         
    
    /*
     * restituisce chiave di firma usato per verificare il token. Decodifica la chiave segreta e crea una chiava
     * HMAC-SHA valida dalla chiave segreta decodificandola poi con il metodo hmacShaKey.
     */
    private Key getSignInKey() {
        byte[] key=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(UserDetails userDetails,
                                Map<String, Object> extraClaims)
    {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*24*60))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails)
    {
        return generateToken(userDetails, new HashMap<>());
    }

    //metodo che permette di vedere validità di token riferito ad un certo utente
    public boolean isValidToken(String token, UserDetails userDetails)
    {
        final String username=extractUsername(token);

        return (username.equals(userDetails.getUsername()))&& isNotExpired(token);
    }
        
        
    private Date extractExpired(String token) {
        return exctractClaim(token, Claims::getExpiration);

    }

    private boolean isNotExpired(String token)
    {
        final Date expiredDate=extractExpired(token);
        return expiredDate.after(new Date());
    } 


}
