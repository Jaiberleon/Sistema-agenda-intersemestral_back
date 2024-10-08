package com.example.demo.security;

import com.example.demo.model.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "jaiberantonioleongarcia12345671007604983jaiberleon";  // Cambia por una clave segura

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles().stream().findFirst().get().getRoleName());
        claims.put("name", user.getNombre() +" " + user.getApellido());
        claims.put("idUser", user.getUserId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
