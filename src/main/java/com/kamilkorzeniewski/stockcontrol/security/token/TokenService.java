package com.kamilkorzeniewski.stockcontrol.security.token;

import com.kamilkorzeniewski.stockcontrol.security.user.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean isTokenExists(Token token) {
        return tokenRepository.existsByToken(token.getToken());
    }

    public Token findToken(String token) {
        return tokenRepository.findTokenByToken(token);
    }

    public Token findByUserId(Long userID) {
        return tokenRepository.findByUserId(userID);
    }

    public Token generateToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token(tokenValue, user);
        return tokenRepository.save(token);
    }
}
