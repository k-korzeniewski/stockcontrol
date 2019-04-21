package com.kamilkorzeniewski.stockcontrol.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByToken(String token);

    Token findTokenByToken(String token);

    Token findByUserId(Long userID);
}
