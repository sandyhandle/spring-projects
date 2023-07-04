package com.example.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    private String jwtSigningKey = "secret";
    private static final String SECRET_KEY = "secret-key";

//    public String extractUserName(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration); }
//
//    public boolean hasClaim(String token, String claimName){
//        final Claims claims = extractAllClaims(token);
//        return claims.get(claimName) != null;
//    }
//
//    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) { return (Claims) Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJwt("santhosh"); }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) { return createToken(claims, userDetails); }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private static boolean isTokenExpired(String token) {
        final Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }




}
