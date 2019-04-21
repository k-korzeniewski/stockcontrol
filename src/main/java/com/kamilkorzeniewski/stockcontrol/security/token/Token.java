package com.kamilkorzeniewski.stockcontrol.security.token;

import com.kamilkorzeniewski.stockcontrol.security.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.persistence.*;
import javax.security.auth.Subject;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token extends AbstractAuthenticationToken {

    @Column(unique = true)
    private final String token;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private final User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime localDateTime;


    public Token(String token) {
        super(null);
        this.token = token;
        this.user = null;
        setAuthenticated(false);
    }

    Token(String token, User user) {
        super(user.getAuthorities());
        this.token = token;
        this.user = user;
        setAuthenticated(true);
    }

    public Token withUser(User user) {
        return new Token(this.token, user);
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }

    @Override
    public Object getPrincipal() {
        return getUser();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
