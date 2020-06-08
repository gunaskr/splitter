package com.splitter.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.splitter.security.config.TokenHolder;
import com.splitter.security.constants.SecurityConstants;
import com.splitter.security.exception.model.UserNotFoundException;
import com.splitter.security.model.Authority;
import com.splitter.security.model.UserAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Service
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {

    @Value("${security.token.secret.key}")
    private String secretKey;
    
    @Autowired
    private TokenHolder tokenHolder;

    public Authentication authenticate(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        tokenHolder.setToken(token);
        final Jws<Claims> tokenData = parseToken(token);
        if (tokenData != null) {
        	final Date expiryDate = new Date((long) tokenData.getBody().get("token_expiration_date"));
        	if( ! expiryDate.after(new Date())) {
        		return null;
        	}
            return new UserAuthentication(getUserFromToken(tokenData));
        }
        return null;
    }

    private Jws<Claims> parseToken(final String token) {
        if (token != null) {
            try {
                return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    private UserDetails getUserFromToken(final Jws<Claims> tokenData) {
        try {
        	String userName = tokenData.getBody().get("username").toString();
        	@SuppressWarnings({ "rawtypes", "unchecked" })
			List<String> object = (List) tokenData.getBody().get("authorities");
        	final List<Authority> authorities = new ArrayList<>();
        	for(String authority: object) {
        		authorities.add(Authority.valueOf(authority));
        	}
			return new JWTUserDetails(authorities,
					userName,
					 (boolean)tokenData.getBody().get("accountNonExpired"), 
					 (boolean)tokenData.getBody().get("accountNonLocked"), 
					 (boolean)tokenData.getBody().get("credentialsNonExpired"), 
					 (boolean)tokenData.getBody().get("enabled"));
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User "
                    + tokenData.getBody().get("username").toString() + " not found");
        }
    }
}
