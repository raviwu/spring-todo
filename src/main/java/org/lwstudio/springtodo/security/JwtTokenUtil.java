package org.lwstudio.springtodo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.lwstudio.springtodo.exception.InvalidJwtException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String APP_SECRET = "so_secret_secret";
    private static final int JWT_EXPIRATION = 604800;

    public static String getUsernameFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid token subject.");
        }
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        if (isTokenExpired(token)) {
            throw new InvalidJwtException("Token expired.");
        }

        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);

        if (username.equals(user.getUsername())) {
            return true;
        } else {
            throw new InvalidJwtException("Token credential not match.");
        }
    }

    private static Date getExpirationDateFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid token expiration info.");
        }
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(APP_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid token format.");
        }
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }
}
