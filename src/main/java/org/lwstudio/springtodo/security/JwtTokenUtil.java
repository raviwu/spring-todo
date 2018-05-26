package org.lwstudio.springtodo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(APP_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }

    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
