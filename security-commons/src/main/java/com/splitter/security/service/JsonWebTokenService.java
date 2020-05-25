package com.splitter.security.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JsonWebTokenService implements TokenService {

    private static int tokenExpirationTime = 30;

    @Value("${security.token.secret.key}")
    private String tokenKey;

    public String getToken(UserDetails user) {
        Map<String, Object> tokenData = new HashMap<>();

        tokenData.put("clientType", "user");
        //tokenData.put("userID", user.getId());
        tokenData.put("username", user.getUsername());
        tokenData.put("authorities", user.getAuthorities());
        tokenData.put("accountNonExpired", user.isAccountNonExpired());
        tokenData.put("accountNonLocked", user.isAccountNonExpired());
		tokenData.put("credentialsNonExpired", user.isCredentialsNonExpired());
		tokenData.put("enabled", user.isEnabled());
        tokenData.put("token_create_date", LocalDateTime.now());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, tokenExpirationTime);
        tokenData.put("token_expiration_date", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

    public static void setTokenExpirationTime(final int tokenExpirationTime) {
        JsonWebTokenService.tokenExpirationTime = tokenExpirationTime;
    }
}
