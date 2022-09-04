package com.example.util;

import com.example.model.ApiUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(UserDetails userDetail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Authorities", userDetail.getAuthorities());

        return Jwts.builder().setClaims(claims).setSubject(userDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public Optional<UserDetails> getUserDetailsFromToken(String token) {

        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        boolean valid = !claims.getExpiration().before(new Date());

        if(!valid) {
            return Optional.empty();
        }

        var username = claims.getSubject();

        Collection<LinkedHashMap<String, String>> claimMap = claims.get("Authorities", Collection.class);
        Collection<String> authorities = claimMap.stream().map(x->x.values().stream().findFirst().orElse("")).collect(Collectors.toList());

        return Optional.of(new ApiUser(username, authorities));
    }
}
