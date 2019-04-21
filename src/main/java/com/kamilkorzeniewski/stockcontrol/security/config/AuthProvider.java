package com.kamilkorzeniewski.stockcontrol.security.config;


import com.kamilkorzeniewski.stockcontrol.security.token.Token;
import com.kamilkorzeniewski.stockcontrol.security.token.TokenService;
import com.kamilkorzeniewski.stockcontrol.security.user.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AuthProvider implements AuthenticationProvider {

    private final TokenService tokenService;

    AuthProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Token token = (Token) authentication;
        /*
        Check if token exists in database
         */
        if (!tokenService.isTokenExists(token)) {
            throw new BadCredentialsException("Invalid token - " + token.getToken());
        }

        /*
            Find user for given token and add user to recived token.
         */
        final User user = tokenService.findToken(token.getToken()).getUser();

        return token.withUser(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Token.class.isAssignableFrom(authentication);
    }
}
