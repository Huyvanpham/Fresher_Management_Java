package dev.manage_fresher_app.service;

import dev.manage_fresher_app.entities.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractID(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Account account) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("full name", account.getEmployee().getName());
        return generateToken(extraClaims, account);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            Account account
    ) {
        return buildToken(extraClaims, account, jwtExpiration);
    }

    public String generateRefreshToken(
            Account user
    ) {
        return buildToken(new HashMap<>(), user, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            Account acc,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(acc.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, Account user) {
        final String id = extractID(token);
        //Todo: Check fullName in token is correct
        return (id.equals(user.getId().toString())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
