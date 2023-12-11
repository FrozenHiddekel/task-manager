package Gihon.task.manager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {

    @Value("${const.secret}")
    private String secret;

    @Value("${const.lifetime}")
    private int lifetime;

    public String generateToken(Authentication authentication){
        UserDetails userDetails = (UserDetailsImplementation)authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+lifetime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getNameFromJvt(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
